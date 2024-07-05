package com.bridge.ipc;

import static com.bridge.SocketServerMock.cleanup;
import static com.bridge.SocketServerMock.makeServerThread;
import static org.junit.jupiter.api.Assertions.fail;

import com.bridge.SocketServerMock;
import com.bridge.renderHandler.render.Frame;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Size;
import com.bridge.renderHandler.sprite.Sprite;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransmitterTest {
    private static Thread SERVER_THREAD;

    @BeforeAll
    static void initServer() {
        cleanup(SocketServerMock.NAMESPACE);
        SERVER_THREAD = makeServerThread(SocketServerMock.NAMESPACE);
        SERVER_THREAD.start();
        while (!Files.exists(SocketServerMock.NAMESPACE)) {
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
        cleanup(SocketServerMock.NAMESPACE);
    }

    private static void sendFrame(List<Sprite> sprites, List<Sound> sounds) {
        try {
            SocketClient socketClient = new SocketClient(SocketServerMock.NAMESPACE);
            Transmitter transmitter = new Transmitter(socketClient);
            Frame frame = new Frame(sprites, sounds);
            transmitter.send(frame);
        } catch (Exception e) {
            fail(e);
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
        Coord position = new Coord(10, 20);
        Size size = new Size(50.5, 75.5);
        Sprite sprite = new Sprite(position, 0, size, Paths.get("sprite.jpg"));
        Sound sound = new Sound(Paths.get("sound.wav"));
        sendFrame(List.of(sprite), List.of(sound));
    }
}
