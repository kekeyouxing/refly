package org.refly;

public class Config {

    public static final int defaultCorePoolSize = Integer.getInteger("org.tom.net.defaultPoolSize", Runtime.getRuntime().availableProcessors());
    public static final int defaultMaximumPoolSize = defaultCorePoolSize;
    private int timeout = 20 * 1000;

    private Encoder encoder;

    private Decoder decoder;

    private Handler handler;
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public Decoder getDecoder() {
        return decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
