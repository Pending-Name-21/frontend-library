package com.bridge.ipc;

import com.bridge.processinputhandler.AEventBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class Receiver {
    private HashMap<String, AEventBuffer> buffers;

    protected void handleMessage(SocketChannel channel) throws IOException {
        // TODO: implement logic
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        if (bytesRead < 0) {
            System.out.print("Got empty message\n");
            return;
        }

        byte[] bytes = new byte[bytesRead];
        buffer.flip();
        buffer.get(bytes);
        String message = new String(bytes);
        System.out.printf("Got message: %s\n", message);
    }
}
