package org.refly.aio;

import org.refly.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

public class AioClient extends AioLifecycle implements Client {
    Config config;

    public AioClient(Config config){
        this.config = config;
    }
    public AioClient(Decoder decoder, Encoder encoder, Handler handler, int timeout){
        config = new Config();
        config.setEncoder(encoder);
        config.setDecoder(decoder);
        config.setHandler(handler);
        config.setTimeout(timeout);
    }
    public AioClient(Decoder decoder, Encoder encoder, Handler handler){
        config = new Config();
        config.setEncoder(encoder);
        config.setDecoder(decoder);
        config.setHandler(handler);
    }
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public int connect(String host, int port) {
        int id = sessionId.getAndIncrement();
        connect(host, port, id);
        return id;
    }

    @Override
    public void connect(String host, int port, int id) {
        try {
            AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open(group);
            socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, false);
            socketChannel.connect(new InetSocketAddress(host, port), id, new CompletionHandler<Void, Integer>() {
                @Override
                public void completed(Void result, Integer id) {
                    worker.registerChannel(socketChannel, id);
                }

                @Override
                public void failed(Throwable exc, Integer attachment) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }
}
