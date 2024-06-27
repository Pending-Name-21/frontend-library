package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.EventGenerator;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.KeyboardEventManager;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
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
            fail();
        }
    }

    @Test
    void checkSocketHandlesEmptyBuffer() {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to delete existing namespace file: " + e.getMessage());
        }

        Receiver receiver = new Receiver();
        SocketServer socketServer = new SocketServer(receiver, NAMESPACE);
        Thread thread = new Thread(socketServer);
        thread.start();

        // Synchronization: Ensure the server is ready before connecting
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                thread.interrupt();
                fail("Test interrupted while waiting for server to initialize: " + e.getMessage());
            }
        }

        // Connect to the server and send an event
        try (SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX)) {
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
            channel.connect(socketAddress);
            channel.write(ByteBuffer.allocate(0));

            // Add a mechanism to verify the event was received and processed
            // For example, add assertions on the receiver to check if the event was handled
            // correctly

        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred: " + e.getMessage());
        } finally {
            // Clean up: Stop the server and delete the namespace file
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail("Failed to join server thread: " + e.getMessage());
            }
            try {
                Files.deleteIfExists(NAMESPACE);
            } catch (IOException e) {
                e.printStackTrace();
                fail("Failed to delete namespace file: " + e.getMessage());
            }
        }
    }

    @Test
    void checkOnlyKeyboardEvent() {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to delete existing namespace file: " + e.getMessage());
        }

        // Start the SocketServer in a new thread
        Receiver receiver = new Receiver();
        receiver.addBuffer(
                event -> {
                    assertNotNull(event.keyboard());
                    assertNull(event.mouse());
                });

        SocketServer socketServer = new SocketServer(receiver, NAMESPACE);
        Thread thread = new Thread(socketServer);
        thread.start();

        // Synchronization: Ensure the server is ready before connecting
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                thread.interrupt();
                fail("Test interrupted while waiting for server to initialize: " + e.getMessage());
            }
        }

        // Connect to the server and send an event
        try (SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX)) {
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
            channel.connect(socketAddress);
            channel.write(new EventGenerator().makeKeyboardOnlyEvent());

            // Add a mechanism to verify the event was received and processed
            // For example, add assertions on the receiver to check if the event was handled
            // correctly

        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred: " + e.getMessage());
        } finally {
            // Clean up: Stop the server and delete the namespace file
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail("Failed to join server thread: " + e.getMessage());
            }
            try {
                Files.deleteIfExists(NAMESPACE);
            } catch (IOException e) {
                e.printStackTrace();
                fail("Failed to delete namespace file: " + e.getMessage());
            }
        }
    }

    @Test
    void checkOnlyMouseEvent() {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to delete existing namespace file: " + e.getMessage());
        }

        // Start the SocketServer in a new thread
        Receiver receiver = new Receiver();
        receiver.addBuffer(
                event -> {
                    assertNull(event.keyboard());
                    assertNotNull(event.mouse());
                });

        SocketServer socketServer = new SocketServer(receiver, NAMESPACE);
        Thread thread = new Thread(socketServer);
        thread.start();

        // Synchronization: Ensure the server is ready before connecting
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                thread.interrupt();
                fail("Test interrupted while waiting for server to initialize: " + e.getMessage());
            }
        }

        // Connect to the server and send an event
        try (SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX)) {
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
            channel.connect(socketAddress);
            channel.write(new EventGenerator().makeKeyboardOnlyEvent());

            // Add a mechanism to verify the event was received and processed
            // For example, add assertions on the receiver to check if the event was handled
            // correctly

        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred: " + e.getMessage());
        } finally {
            // Clean up: Stop the server and delete the namespace file
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail("Failed to join server thread: " + e.getMessage());
            }
            try {
                Files.deleteIfExists(NAMESPACE);
            } catch (IOException e) {
                e.printStackTrace();
                fail("Failed to delete namespace file: " + e.getMessage());
            }
        }
    }

    @Test
    void verifyServerCleansNamespace() {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to delete existing namespace file: " + e.getMessage());
        }

        Receiver receiver = new Receiver();
        SocketServer socketServer = new SocketServer(receiver, NAMESPACE);
        Thread thread = new Thread(socketServer);
        thread.start();
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                fail("Test interrupted while waiting for server to initialize: " + e.getMessage());
            }
        }

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }
        assertFalse(Files.exists(NAMESPACE));
    }
}
