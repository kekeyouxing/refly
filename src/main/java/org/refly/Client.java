package org.refly;

import java.util.concurrent.ExecutorService;

public interface Client {

    void setConfig(Config config);

    int connect(String host, int port);

    void connect(String host, int port, int id);

    ExecutorService getExecutorService();
}
