package com.bridge.ipc;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The {@code SocketServer} class implements a UNIX domain socket server that listens for incoming connections
 * and processes messages using a {@link Receiver}.
 */
public class SocketServer implements Runnable {

    /**
     * The receiver responsible for handling incoming messages.
     */
    private final Receiver receiver;

    /**
     * The path representing the namespace for the UNIX domain socket.
     */
    private final Path namespace;

    /**
     * Constructs a {@code SocketServer} with the specified receiver and namespace.
     *
     * @param receiver  the receiver to handle incoming messages
     * @param namespace the namespace for the UNIX domain socket
     */
    public SocketServer(Receiver receiver, String namespace) {
        this.receiver = receiver;
        // TODO: validate the path
        this.namespace = Path.of(System.getProperty("java.io.tmpdir"), namespace);
    }

    /**
     * Runs the socket server, listening for and accepting client connections,
     * reading messages, and passing them to the receiver for handling.
     */
    @Override
    public void run() {
        // TODO: handle thread interruption
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(namespace);
        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            server.bind(socketAddress);
            System.out.printf("Listening on %s\n", namespace);
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                SocketChannel client = server.accept();
                buffer.clear();
                int bytesRead = client.read(buffer);
                if (bytesRead < 0) {
                    System.out.print("Got empty message\n");
                    return;
                }
                buffer.rewind();
                receiver.handleMessage(buffer);
            }

        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
        }
        System.out.println("Closed connection");
        flush();
    }

    /**
     * Cleans up the UNIX domain socket namespace by deleting the namespace file.
     */
    public void flush() {
        try {
            Files.deleteIfExists(namespace);
        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
        }
    }
}
