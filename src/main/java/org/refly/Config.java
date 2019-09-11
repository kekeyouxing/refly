package org.refly;

public class Config {

    public static final int defaultCorePoolSize = Integer.getInteger("org.tom.net.defaultPoolSize", Runtime.getRuntime().availableProcessors());
    public static final int defaultMaximumPoolSize = defaultCorePoolSize;

}
