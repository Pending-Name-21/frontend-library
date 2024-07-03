package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.fail;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.processinputhandler.KeyboardEventManager;
import com.bridge.processinputhandler.MouseEventManager;
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
import java.util.concurrent.atomic.AtomicBoolean;

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
        Sprite sprite = new Sprite(position, 0, size, Paths.get("sprite.jpg"));
        sendFrame(List.of(sprite), new ArrayList<>());
    }

    @Test
    void sendASound() {
        Sound sound = new Sound(Paths.get("sound.wav"));
        sendFrame(new ArrayList<>(), List.of(sound));
    }

    @Test
    void sendCompleteFrame() {
        Path socketPath = Path.of("/tmp/socket_console");
        SocketClient socketClient = new SocketClient(socketPath);
        Transmitter transmitter = new Transmitter(socketClient);
        try {
            transmitter.sendLabel("fromfrontend", "red", 10, 50);
        } catch (RenderException e) {
            throw new RuntimeException(e);
        }
        try {
            for (int i = 0; i < 500; i++) {
                Coord position = new Coord(i, 10);
                Size size = new Size(50.5, 75.5);
                Sprite sprite = new Sprite(position, i,size, Paths.get("assets/images/pacman.jpeg"));
                Sound sound = new Sound(Paths.get(""));
                sound.setPlaying(false);
                Frame frame = new Frame(List.of(sprite), List.of(sound));
                transmitter.send(frame);
                System.out.println("Frame " + (i + 1) + " enviado");

                // Esperar 16 milisegundos para mantener 60 fps
                Thread.sleep(10);
            }
        } catch (RenderException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el frame: " + e.getMessage());
        }

        try {
            transmitter.sendLabel("from frontend two", "yellow", 10, 50);
        } catch (RenderException e) {
            throw new RuntimeException(e);
        }
    }
}
