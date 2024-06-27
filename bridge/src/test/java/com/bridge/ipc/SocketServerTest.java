package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bridge.EventGenerator;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.KeyboardEventManager;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SocketServerTest {
    private static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "events-socket.sock");

    @Test
    @Disabled
    void startServer() {
        SocketServer socketServer = new SocketServer(new Receiver(), NAMESPACE);
        socketServer.flush();
        socketServer.run();
        System.out.println("Closed socket server");
    }

    @Test
    void checkReceivedEventPublication() {
        Receiver receiver = new Receiver();
        SocketServer socketServer = new SocketServer(receiver, NAMESPACE);
        socketServer.flush();
        Thread thread = new Thread(socketServer);
        KeyboardEventManager keyboardManager = new KeyboardEventManager();
        keyboardManager.subscribe(
                event -> {
                    assertEquals("KeyPressed", event.type());
                    assertEquals("Return", event.key());
                    thread.interrupt();
                });
        receiver.addBuffer(keyboardManager);
        thread.start();

        InputVerifier inputVerifier = new InputVerifier(List.of(keyboardManager));
        try (SocketChannel client = SocketChannel.open(StandardProtocolFamily.UNIX)) {
            EventGenerator generator = new EventGenerator();
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
            client.connect(socketAddress);
            for (int i = 0; i < 50; i++) {
                client.write(generator.makeEvent());
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        inputVerifier.check();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Thread was interrupted: " + e.getMessage());
        }
    }
}
