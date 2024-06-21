package com.bridge.ipc;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class SocketServer implements Runnable {
    private final Receiver receiver;
    private final Path namespace;

    public SocketServer(Receiver receiver, String namespace) {
        this.receiver = receiver;
        // TODO: validate the path
        this.namespace = Path.of(System.getProperty("java.io.tmpdir"), namespace);
    }

    // TODO: handle thread interruption
    @Override
    public void run() {
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(namespace);
        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            server.bind(socketAddress);
            System.out.printf("Listening on %s\n", namespace);

            while (true) {
                SocketChannel client = server.accept();
                receiver.handleMessage(client);
            }

        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
        }
        System.out.println("Closed connection");
        flush();
    }

    public void flush() {
        try {
            Files.deleteIfExists(namespace);
        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
        }
    }
}
