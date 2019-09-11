package org.refly;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Executors {

    private static ThreadPoolExecutor executor;
    private static int corePoolSize = 50;
    private static int maximumPoolSize = Integer.MAX_VALUE;
    private static long keepAliveTime = 0L;

    public static ThreadPoolExecutor getExecutor(){
        if (executor == null){
            synchronized (Executors.class){
                if (executor == null){
                    TimeUnit unit;
                    BlockingQueue workQueue;
                    executor = new ThreadPoolExecutor(
                            Config.defaultCorePoolSize,
                            Config.defaultMaximumPoolSize,
                            keepAliveTime,
                            TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>());
                    executor.prestartCoreThread();
                }
            }
        }
        return executor;
    }

}
