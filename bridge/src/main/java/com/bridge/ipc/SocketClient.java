package com.bridge.ipc;

import com.bridge.core.exceptions.ipc.SocketClientException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.core.handlers.LogHandler;
import com.bridge.renderHandler.render.RenderManager;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.logging.Level;

/**
 * This class provides a client-side connection for inter-process communication (IPC)
 * using Unix domain sockets.
 */
public class SocketClient {
    public static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "socket_console");
    private final Path namespace;
    private int failedWriteAttempts;

    /**
     * Constructs a new `SocketClient` instance connected to the specified namespace.
     *
     * @param namespace The name of the Unix domain socket to connect to.
     */
    public SocketClient(Path namespace) {
        this.namespace = namespace;
    }

    /**
     * Sends the provided byte buffer through the established socket connection.
     * This method performs a non-blocking write, sending as much data as possible
     * from the buffer until it is empty.
     *
     * @param buffer The byte buffer containing the data to be sent.
     */
    protected void send(ByteBuffer buffer) throws RenderException, SocketClientException {
        UnixDomainSocketAddress serverAddress = UnixDomainSocketAddress.of(namespace);
        try (SocketChannel channel = SocketChannel.open(serverAddress)) {
            if (!channel.isConnected()) {
                channel.connect(serverAddress);
            }

            while (buffer.hasRemaining()) {
                channel.write(buffer);
                failedWriteAttempts = 0;
            }

        } catch (ConnectException | UnsupportedOperationException e) {
            LogHandler.log(Level.WARNING, "Channel disconnected");
            throw new SocketClientException("Channel disconnected");
        } catch (ClosedChannelException e) {
            LogHandler.log(Level.WARNING, "Could not send frame", e);
            handleSentError();
        } catch (IOException e) {
            throw new RenderException("Unexpected exception", e);
        }
    }

    private void handleSentError() throws RenderException {
        failedWriteAttempts++;
        if (failedWriteAttempts >= RenderManager.FRAMES_LOST_THRESHOLD) {
            failedWriteAttempts = 0;
            throw new RenderException("Reached frames lost threshold");
        }
    }

    public static void scout() throws RenderException {
        boolean success = false;
        while (!success) {
            try (SocketChannel ignored =
                    SocketChannel.open(UnixDomainSocketAddress.of(NAMESPACE))) {
                success = true;
            } catch (IOException e) {
                LogHandler.log(Level.WARNING, "Could not establish connection");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RenderException(e.getMessage(), e);
            }
        }
    }
}
