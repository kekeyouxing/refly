package org.refly;

import java.nio.channels.Channel;

public interface Worker {
    void registerChannel(Channel channel, int sessionId);
}
