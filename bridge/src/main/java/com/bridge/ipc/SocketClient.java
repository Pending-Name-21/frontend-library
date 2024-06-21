package com.bridge.ipc;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClient {
    private SocketChannel channel;

    public SocketClient(String namespace) {
        try {
            channel = SocketChannel.open(StandardProtocolFamily.UNIX);
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(namespace);
            channel.connect(socketAddress);
        } catch (IOException e) {
            // TODO: handle error
            e.printStackTrace();
        }
    }

    protected void send(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            try {
                channel.write(buffer);
            } catch (IOException e) {
                // TODO: handle error
                e.printStackTrace();
            }
        }
    }
}
