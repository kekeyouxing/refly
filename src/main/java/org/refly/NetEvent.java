package org.refly;

public interface NetEvent {

    void notifySessionOpened(Session session);

    void notifySessionClosed(Session session);

    void notifyMessageReceived(Session session, Object message);

    void notifyExceptionCaught(Session session, Throwable throwable);
}
