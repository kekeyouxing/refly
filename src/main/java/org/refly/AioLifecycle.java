package org.refly;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class AioLifecycle extends AbstractLifecycle{

    AsynchronousChannelGroup group = null;
    AtomicInteger sessionId = new AtomicInteger();
    ExecutorService executorService = null;
    @Override
    public void init() {
        ExecutorService executorService = createExecutor();
        AsynchronousChannelGroup channelGroup= crateGroup();

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
