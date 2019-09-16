package org.refly.buffer;

public class BufferUtils {

    public static int normalizeBufferSize(int capacity){
        int p = capacity >>> 10;
        int r = capacity & 1023;
        if(r != 0){
            p++;
        }
        return p << 10;
    }

}
