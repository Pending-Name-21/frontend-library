package com.bridge.ipc;

import com.bridge.core.handlers.LogHandler;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

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
    public SocketServer(Receiver receiver, Path namespace) {
        this.receiver = receiver;
        this.namespace = namespace;
    }

    /**
     * Runs the socket server, listening for and accepting client connections,
     * reading messages, and passing them to the receiver for handling.
     */
    @Override
    public void run() {
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(namespace);
        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            server.bind(socketAddress);
            LogHandler.log(Level.INFO, String.format("Listening on %s\n", namespace));
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            boolean run = true;
            while (run) {
                try {
                    SocketChannel client = server.accept();
                    buffer.clear();
                    int bytesRead = client.read(buffer);
                    if (bytesRead < 0) {
                        LogHandler.log(Level.INFO, "Got empty message");
                        continue;
                    }
                    buffer.rewind();
                    receiver.handleMessage(buffer);
                } catch (ClosedByInterruptException e) {
                    flush();
                    LogHandler.log(Level.INFO, "Closed connection");
                    run = false;
                }
            }

        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
            flush();
        }
    }

    /**
     * Cleans up the UNIX domain socket namespace by deleting the namespace file.
     */
    public void flush() {
        try {
            Files.deleteIfExists(namespace);
        } catch (IOException e) {
            LogHandler.log(
                    Level.WARNING,
                    String.format(
                            "Could not remove namespace: %s\nBecause of %s",
                            namespace, e.getMessage()));
        }
    }
}
