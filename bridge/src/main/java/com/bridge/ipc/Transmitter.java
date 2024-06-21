package com.bridge.ipc;

import java.nio.ByteBuffer;

public class Transmitter {
    private SocketClient socketClient;

    public void send(String message){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();
    }
}
