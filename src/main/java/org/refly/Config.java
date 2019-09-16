package org.refly;

public class Config {

    public static final int defaultCorePoolSize = Integer.getInteger("org.tom.net.defaultPoolSize", Runtime.getRuntime().availableProcessors());
    public static final int defaultMaximumPoolSize = defaultCorePoolSize;
    private int timeout = 20 * 1000;

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }
}
