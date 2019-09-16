package org.refly.aio;

import org.refly.*;
import org.refly.exception.NetException;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class AioLifecycle extends AbstractLifecycle {

    AsynchronousChannelGroup group = null;
    AtomicInteger sessionId = new AtomicInteger();
    ExecutorService executorService = null;
    Config config = null;
    Worker worker = null;
    @Override
    public void init() {
        if(config == null){
            throw new NetException("server config is missing!");
        }
        this.executorService = createExecutor();
        this.group= crateGroup();
        this.worker = new AioWorker(config, new DefaultNetEvent(config));
    }

    private ExecutorService createExecutor(){
        return Executors.getExecutor();
    }
    private AsynchronousChannelGroup crateGroup() {
        try {
            return AsynchronousChannelGroup.withThreadPool(executorService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        if(group != null){
            group.shutdown();
        }
    }
}
