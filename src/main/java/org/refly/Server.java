package org.refly;

import java.util.concurrent.ExecutorService;

public interface Server extends Lifecycle{

    ExecutorService getExecutorService();

    void setConfig(Config config);

    void listen(String host, int port);

}
