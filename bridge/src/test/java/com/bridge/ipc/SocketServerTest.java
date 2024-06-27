package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.EventGenerator;
import com.bridge.processinputhandler.KeyboardEventManager;
import com.bridge.processinputhandler.MouseEventManager;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class SocketServerTest {
    private static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "events-socket.sock");

    public static Thread startServer(Receiver receiver) {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to delete existing namespace file: " + e.getMessage());
        }
        SocketServer socketServer = new SocketServer(receiver, NAMESPACE);
        Thread thread = new Thread(socketServer);
        thread.start();
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                thread.interrupt();
                fail("Test interrupted while waiting for server to initialize: " + e.getMessage());
            }
        }
        return thread;
    }

    public static void sendToServer(ByteBuffer buffer) {
        try (SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX)) {
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
            channel.connect(socketAddress);
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred: " + e.getMessage());
        }
    }

    @Test
    void checkCompleteEventIsReceived() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);
        receiver.addBuffer(mouseEventManager);
        Thread thread = startServer(receiver);

        sendToServer(new EventGenerator().makeEvent());
        assertFalse(keyboardEventManager.getEvents().isEmpty());
        assertFalse(mouseEventManager.getEvents().isEmpty());

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }
    }

    @Test
    void checkSocketHandlesEmptyBuffer() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);
        receiver.addBuffer(mouseEventManager);
        Thread thread = startServer(receiver);

        sendToServer(ByteBuffer.allocate(0));
        assertTrue(keyboardEventManager.getEvents().isEmpty());
        assertTrue(mouseEventManager.getEvents().isEmpty());

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }
    }

    @Test
    void checkOnlyKeyboardEvent() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);
        receiver.addBuffer(mouseEventManager);
        Thread thread = startServer(receiver);

        sendToServer(new EventGenerator().makeKeyboardOnlyEvent());
        assertFalse(keyboardEventManager.getEvents().isEmpty());
        assertTrue(mouseEventManager.getEvents().isEmpty());

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }
    }

    @Test
    void checkOnlyMouseEvent() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);
        receiver.addBuffer(mouseEventManager);
        Thread thread = startServer(receiver);

        sendToServer(new EventGenerator().makeMouseOnlyEvent());
        assertTrue(keyboardEventManager.getEvents().isEmpty());
        assertFalse(mouseEventManager.getEvents().isEmpty());

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }
    }

    @Test
    void verifyServerCleansNamespace() {
        Thread thread = startServer(new Receiver());

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
