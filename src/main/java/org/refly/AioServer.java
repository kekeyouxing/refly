package org.refly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

public class AioServer extends AioLifecycle implements Server{

    Logger logger= LoggerFactory.getLogger(AioServer.class);

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public void setConfig() {

    }

    @Override
    public void listen(String host, int port) {
        start();
        listen(bind(host, port));
        logger.info("start server host: {}, port: {}", host, port);
    }

    private void listen(AsynchronousServerSocketChannel serverSocketChannel) {
        serverSocketChannel.accept(sessionId.getAndIncrement(), new CompletionHandler<AsynchronousSocketChannel, Integer>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Integer sessionId) {

            }

            @Override
            public void failed(Throwable exc, Integer attachment) {

            }
        });
    }

    private AsynchronousServerSocketChannel bind(String host, int port) {
        AsynchronousServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open(group);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 10 * 1024);
            serverSocketChannel.bind(new InetSocketAddress(host, port), 1024);
        } catch (IOException e) {
            logger.error("ServerSocket bind err", e);
        }
        return serverSocketChannel;
    }


}
