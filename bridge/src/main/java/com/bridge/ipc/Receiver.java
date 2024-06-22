package com.bridge.ipc;

import com.bridge.processinputhandler.AEventBuffer;
import CoffeeTime.InputEvents.KeyboardEvent;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class Receiver {
    private HashMap<String, AEventBuffer> buffers;
    private final ByteBuffer buffer;

    public Receiver() {
        buffer = ByteBuffer.allocate(1024);
    }

    protected void handleMessage(SocketChannel channel) throws IOException {
        buffer.clear();
        int bytesRead = channel.read(buffer);
        if (bytesRead < 0) {
            System.out.print("Got empty message\n");
            return;
        }

        buffer.rewind();
        KeyboardEvent keyboardEvent = KeyboardEvent.getRootAsKeyboardEvent(buffer);
        System.out.printf("Got message: %s\n", keyboardEvent.name());
    }
}
