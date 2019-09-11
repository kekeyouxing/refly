package org.refly;

public interface Lifecycle {
    void start();

    void stop();

    boolean isStarted();

    boolean isStopped();
}
