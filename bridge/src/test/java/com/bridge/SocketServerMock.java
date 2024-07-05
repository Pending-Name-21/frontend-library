package com.bridge;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class SocketServerMock {
    public static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "test_socket_console");

    public static Thread makeServerThread(Path namespace) {
        return new Thread(
                () -> {
                    UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(namespace);
                    try (ServerSocketChannel server =
                            ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
                        server.bind(socketAddress);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (!Thread.currentThread().isInterrupted()) {
                            try (SocketChannel client = server.accept()) {
                                client.read(buffer);
                                buffer.clear();
                            } catch (IOException e) {
                                if (Thread.currentThread().isInterrupted()) {
                                    break;
                                }
                                fail("Server failed to accept connection", e);
                            }
                        }
                    } catch (IOException e) {
                        fail("Server failed to start", e);
                    }
                });
    }

    public static void cleanup(Path namespace) {
        try {
            Files.deleteIfExists(namespace);
        } catch (IOException e) {
            fail("Failed to delete namespace file", e);
        }
    }
}
