package org.refly.aio;

import org.refly.Config;
import org.refly.NetEvent;
import org.refly.Worker;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channel;

public class AioWorker implements Worker {
    private Config config;
    private NetEvent netEvent;
    public AioWorker(Config config, NetEvent netEvent) {
        this.config = config;
        this.netEvent = netEvent;
    }

    @Override
    public void registerChannel(Channel channel, int sessionId) {

        try {

            AsynchronousSocketChannel socketChannel = (AsynchronousSocketChannel) channel;
            socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, false);

            AioSession session = new AioSession(sessionId, config, netEvent, socketChannel);
            netEvent.notifySessionOpened(session);
            session._read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
