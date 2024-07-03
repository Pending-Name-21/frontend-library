package com.bridge.ipc;

import com.bridge.core.handlers.LogHandler;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/**
 * The {@code SocketServer} class implements a UNIX domain socket server that listens for incoming connections
 * and processes messages using a {@link Receiver}.
 */
public class SocketServer implements Runnable {
    public static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "events-socket.sock");
    private final Receiver receiver;
    private final Path namespace;
    private final AtomicBoolean isServerRunning;

    /**
     * Constructs a {@code SocketServer} with the specified receiver and namespace.
     *
     * @param receiver        the receiver to handle incoming messages
     * @param namespace       the namespace for the UNIX domain socket
     * @param isServerRunning the atomic boolean to control the server running state
     */
    public SocketServer(Receiver receiver, Path namespace, AtomicBoolean isServerRunning) {
        this.receiver = receiver;
        this.namespace = namespace;
        this.isServerRunning = isServerRunning;
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
            listen(server);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogHandler.log(Level.SEVERE, "Socket Server has been interrupted", e);
        } catch (ClosedChannelException e) {
            LogHandler.log(Level.SEVERE, "Socket Server channel has been closed", e);
        } catch (IOException e) {
            LogHandler.log(Level.SEVERE, "Unexpected exception", e);
        } finally {
            flush();
        }
    }

    /**
     * Cleans up the UNIX domain socket namespace by deleting the namespace file.
     */
    private void flush() {
        try {
            Files.deleteIfExists(namespace);
            LogHandler.log(Level.INFO, String.format("Deleted namespace %s", namespace));
        } catch (IOException e) {
            LogHandler.log(
                    Level.SEVERE, String.format("Could not delete namespace %s", namespace), e);
        }
    }

    /**
     * Listens for incoming client connections and handles them.
     *
     * @param server the server socket channel to listen on
     */
    private void listen(ServerSocketChannel server) throws IOException, InterruptedException {
        LogHandler.log(Level.INFO, String.format("Listening on %s", namespace));
        while (isServerRunning.get()) {
            try (SocketChannel channel = server.accept()) {
                LogHandler.log(Level.INFO, String.format("Accepted client %s", channel));
                manageChannel(channel);
            }
        }
    }

    /**
     * Manages the communication channel with a client, reading messages and passing them to the receiver.
     *
     * @param channel the client socket channel to manage
     */
    private void manageChannel(SocketChannel channel) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (channel.isOpen() && isServerRunning.get()) {
            buffer.clear();
            int bytesRead = channel.read(buffer);
            if (bytesRead <= 0) {
                LogHandler.log(Level.INFO, "Nothing to read");
                Thread.sleep(100);
                continue;
            }
            buffer.flip();
            receiver.handleMessage(buffer);
        }
    }
}
