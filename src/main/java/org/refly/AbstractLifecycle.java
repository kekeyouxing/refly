package org.refly;

public abstract class AbstractLifecycle implements Lifecycle{

    volatile boolean started = false;

    public abstract void init();

    public abstract void destroy();

    @Override
    public void start() {
        if (!isStarted()){
            synchronized (this){
                if(!isStarted()){
                    init();
                    started = true;
                }
            }
        }
    }

    @Override
    public void stop() {
        if (!isStopped()){
            synchronized (this){
                if(!isStopped()){
                    destroy();
                    started = false;
                }
            }
        }
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean isStopped() {
        return !started;
    }
}
