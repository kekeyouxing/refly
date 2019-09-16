package org.refly;

public class DefaultNetEvent implements NetEvent{

    Config config;

    public DefaultNetEvent(Config config) {
        this.config = config;
    }

    @Override
    public void notifySessionOpened(Session session) {
    }

    @Override
    public void notifySessionClosed(Session session) {

    }

    @Override
    public void notifyMessageReceived(Session session, Object message) {

    }

    @Override
    public void notifyExceptionCaught(Session session, Throwable throwable) {

    }
}
