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
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class SocketServerTest {
    public static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "test-events-socket.sock");

    public static Thread startServer(Receiver receiver, AtomicBoolean atomicBoolean) {
        SocketServer socketServer = new SocketServer(receiver, NAMESPACE, atomicBoolean);
        Thread thread = new Thread(socketServer);
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail(e.getMessage(), e.getCause());
        }
        return thread;
    }

    public static void sendToServer(ByteBuffer buffer) {
        try (SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX)) {
            UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
            channel.connect(socketAddress);
            channel.write(buffer);
        } catch (IOException e) {
            fail(e.getMessage(), e);
        }
    }

    public static Receiver makeReceiver(
            KeyboardEventManager keyboardEventManager, MouseEventManager mouseEventManager) {
        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);
        receiver.addBuffer(mouseEventManager);
        return receiver;
    }

    @Test
    void checkCompleteEventIsReceived() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        Thread thread =
                startServer(makeReceiver(keyboardEventManager, mouseEventManager), atomicBoolean);

        sendToServer(new EventGenerator().makeEvent());
        for (int i = 0; i < 3; i++) {
            if (mouseEventManager.getEvents().isEmpty()
                    || keyboardEventManager.getEvents().isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        atomicBoolean.set(false);
        try {
            thread.join(100);
            if (thread.isAlive()) {
                thread.interrupt();
            }
        } catch (InterruptedException e) {
            fail("Failed to join server thread", e);
        }

        assertFalse(keyboardEventManager.getEvents().isEmpty());
        assertFalse(mouseEventManager.getEvents().isEmpty());
    }

    @Test
    void checkSocketHandlesEmptyBuffer() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        Thread thread =
                startServer(makeReceiver(keyboardEventManager, mouseEventManager), atomicBoolean);

        sendToServer(ByteBuffer.allocate(0));
        for (int i = 0; i < 3; i++) {
            if (mouseEventManager.getEvents().isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    fail(e.getMessage(), e.getCause());
                }
            }
        }

        atomicBoolean.set(false);
        try {
            thread.join(100);
            if (thread.isAlive()) {
                thread.interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }

        assertTrue(keyboardEventManager.getEvents().isEmpty());
        assertTrue(mouseEventManager.getEvents().isEmpty());
    }

    @Test
    void checkOnlyKeyboardEvent() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        Thread thread =
                startServer(makeReceiver(keyboardEventManager, mouseEventManager), atomicBoolean);

        sendToServer(new EventGenerator().makeKeyboardOnlyEvent());
        for (int i = 0; i < 3; i++) {
            if (mouseEventManager.getEvents().isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        atomicBoolean.set(false);
        try {
            thread.join(100);
            if (thread.isAlive()) {
                thread.interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }
        assertTrue(mouseEventManager.getEvents().isEmpty());
        assertFalse(keyboardEventManager.getEvents().isEmpty());
    }

    @Test
    void checkOnlyMouseEvent() {
        KeyboardEventManager keyboardEventManager = new KeyboardEventManager();
        MouseEventManager mouseEventManager = new MouseEventManager();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        Thread thread =
                startServer(makeReceiver(keyboardEventManager, mouseEventManager), atomicBoolean);

        sendToServer(new EventGenerator().makeMouseOnlyEvent());
        for (int i = 0; i < 3; i++) {
            if (mouseEventManager.getEvents().isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        atomicBoolean.set(false);
        try {
            thread.join(100);
            if (thread.isAlive()) {
                thread.interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread: " + e.getMessage());
        }

        assertFalse(mouseEventManager.getEvents().isEmpty());
        assertTrue(keyboardEventManager.getEvents().isEmpty());
    }

    @Test
    void verifyServerCleansNamespace() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        SocketServer socketServer = new SocketServer(new Receiver(), NAMESPACE, atomicBoolean);
        Thread thread = new Thread(socketServer);
        thread.start();
        thread.interrupt();
        try {
            thread.join(1000);
            if (thread.isAlive()) {
                fail("Socket server did not exited gracefully");
            }
            socketServer.flush();

            assertFalse(Files.exists(NAMESPACE));
        } catch (InterruptedException e) {
            fail("Failed to join server thread", e);
        }
    }
}
