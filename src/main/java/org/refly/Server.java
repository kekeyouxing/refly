package org.refly;

import java.util.concurrent.ExecutorService;

public interface Server {
    ExecutorService getExecutorService();

    void setConfig();

    void listen(String host, int port);
}
