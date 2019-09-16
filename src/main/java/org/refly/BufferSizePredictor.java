package org.refly;

public interface BufferSizePredictor {

    int nextBufferSize();

    void previousReceivedBufferSize(int previousReceivedBufferSize);
}
