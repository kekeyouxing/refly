package org.refly.aio;

import org.refly.BufferSizePredictor;
import org.refly.Config;
import org.refly.NetEvent;
import org.refly.Session;
import org.refly.buffer.AdaptiveBufferSizePredictor;
import org.refly.buffer.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AioSession implements Session {
    private static Logger log = LoggerFactory.getLogger(AioSession.class);
    private final int sessionId;

    private Config config;

    private NetEvent netEvent;

    private AsynchronousSocketChannel socket;

    private AtomicBoolean closed = new AtomicBoolean(false);

    private long readBytes = 0;

    public int getSessionId() {
        return sessionId;
    }

    private final BufferSizePredictor bufferSizePredictor = new AdaptiveBufferSizePredictor();
    public AioSession(int sessionId, Config config, NetEvent netEvent, AsynchronousSocketChannel socketChannel) {
        this.sessionId = sessionId;
        this.config = config;
        this.netEvent = netEvent;
        this.socket = socketChannel;
    }

    public ByteBuffer allocateBufferSize(){
        int size = BufferUtils.normalizeBufferSize(bufferSizePredictor.nextBufferSize());
        return ByteBuffer.allocate(size);
    }
    public void _read() {
        ByteBuffer byteBuffer = allocateBufferSize();
        socket.read(byteBuffer, config.getTimeout(), TimeUnit.MILLISECONDS, this, new InputCompletionHandler(byteBuffer));
    }

    private class InputCompletionHandler implements CompletionHandler<Integer, AioSession>{
        private final ByteBuffer buf;
        public InputCompletionHandler(ByteBuffer buf) {
            this.buf = buf;
        }

        @Override
        public void completed(Integer result, AioSession session) {
            //1. 什么时候result < 0
            // socket已经关闭,result返回-1

            //2. 一个socketChannel对byteBuffer的输入是多线程的还是单线程的，
            // 也就是说这段代码是又好多线程执行还是一个线程执行
            if(result < 0){
                log.info("The session {} input channel is shutdown {}", session.getSessionId(), buf);
                session.closeNow();
            }
            session.bufferSizePredictor.previousReceivedBufferSize(result);
            session.readBytes += result;
            buf.flip();
            try {
                //config.getDecoder().decode(buf, session);
            } catch (Throwable t) {
                netEvent.notifyExceptionCaught(session, t);
            } finally {
                _read();
            }
        }

        @Override
        public void failed(Throwable exc, AioSession session) {

        }
    }

    private void closeNow() {
        if(closed.weakCompareAndSet(false, true)){
            try {
                socket.close();
            } catch (IOException e) {
                log.error("The session {} close exception", sessionId);
            }finally {
                netEvent.notifySessionClosed(this);
            }
        }else{
            log.info("session {} already closed", sessionId);
        }
    }
}
