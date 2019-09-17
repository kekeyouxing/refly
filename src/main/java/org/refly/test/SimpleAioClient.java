package org.refly.test;

import org.refly.AbstractLifecycle;
import org.refly.Client;
import org.refly.Config;
import org.refly.aio.AioClient;

public class SimpleAioClient extends AbstractLifecycle {

    Client client;

    Config config;

    public SimpleAioClient(Config config){
        this.client = new AioClient(config);
        this.config = config;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
