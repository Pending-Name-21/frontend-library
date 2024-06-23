package com.bridge.ipc;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * This class provides a client-side connection for inter-process communication (IPC)
 * using Unix domain sockets.
 */
public class SocketClient {
    private SocketChannel channel;

    /**
     * Constructs a new `SocketClient` instance connected to the specified namespace.
     *
     * @param namespace The name of the Unix domain socket to connect to.
     */
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

    /**
     * Sends the provided byte buffer through the established socket connection.
     * This method performs a non-blocking write, sending as much data as possible
     * from the buffer until it is empty.
     *
     * @param buffer The byte buffer containing the data to be sent.
     */
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
