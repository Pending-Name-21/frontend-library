package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.fail;

import com.bridge.renderHandler.render.Frame;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Size;
import com.bridge.renderHandler.sprite.Sprite;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransmitterTest {
    private static Thread SERVER_THREAD;
    public static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "test_socket_console");

    public static Thread startServer() {
        return new Thread(
                () -> {
                    UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(NAMESPACE);
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

    public static void cleanup() {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            fail("Failed to delete namespace file", e);
        }
    }

    @BeforeAll
    static void initServer() {
        cleanup();
        SERVER_THREAD = startServer();
        SERVER_THREAD.start();
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                fail("Test interrupted while waiting for server to initialize", e);
            }
        }
    }

    @AfterAll
    static void stopServer() {
        SERVER_THREAD.interrupt();
        try {
            SERVER_THREAD.join();
        } catch (InterruptedException e) {
            fail("Failed to join server thread", e);
        }
        cleanup();
    }

    private static void sendFrame(List<Sprite> sprites, List<Sound> sounds) {
        try {
            SocketClient socketClient = new SocketClient(NAMESPACE);
            Transmitter transmitter = new Transmitter(socketClient);
            Frame frame = new Frame(sprites, sounds);
            transmitter.send(frame);
        } catch (Exception e) {
            fail("Exception occurred", e);
        }
    }

    @Test
    void sendASprite() {
        Coord position = new Coord(10, 20);
        Size size = new Size(50.5, 75.5);
        Sprite sprite = new Sprite(position, size, Paths.get("sprite.jpg"));
        sendFrame(List.of(sprite), new ArrayList<>());
    }

    @Test
    void sendASound() {
        Sound sound = new Sound(Paths.get("sound.wav"));
        sendFrame(new ArrayList<>(), List.of(sound));
    }

    @Test
    void sendCompleteFrame() {
        Coord position = new Coord(10, 20);
        Size size = new Size(50.5, 75.5);
        Sprite sprite = new Sprite(position, size, Paths.get("sprite.jpg"));
        Sound sound = new Sound(Paths.get("sound.wav"));
        sendFrame(List.of(sprite), List.of(sound));
    }
}
