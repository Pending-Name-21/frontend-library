package com.bridge;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.GameException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.ipc.SocketClient;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private static Thread SERVER_THREAD;
    private TestGameSettings gameSettings;
    private Game game;

    @BeforeAll
    static void startServer() {
        SocketServerMock.cleanup(SocketClient.NAMESPACE);
        SERVER_THREAD = SocketServerMock.makeServerThread(SocketClient.NAMESPACE);
        SERVER_THREAD.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            fail(e);
        }
    }

    @AfterAll
    static void stopServer() {
        SERVER_THREAD.interrupt();
        try {
            SERVER_THREAD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Failed to join server thread");
        }
        SocketServerMock.cleanup(SocketClient.NAMESPACE);
    }

    @BeforeEach
    void setUp() {
        gameSettings = new TestGameSettings();
        game = new Game(gameSettings);
    }

    @Test
    void testRender() {
        game.render();
    }

    @Test
    void testRun() throws InterruptedException {
        gameSettings.setGameOver(false);
        Thread gameThread =
                new Thread(
                        () -> {
                            try {
                                game.run();
                            } catch (GameException e) {
                                fail("GameException occurred: " + e.getMessage());
                            }
                        });

        gameThread.start();

        Thread stopperThread =
                new Thread(
                        () -> {
                            try {
                                Thread.sleep(1000);
                                gameSettings.setGameOver(true);
                            } catch (InterruptedException e) {
                                fail("InterruptedException occurred", e);
                            }
                        });

        stopperThread.start();
        stopperThread.join();
        gameThread.join();
    }

    @Test
    public void testFramesCount() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        game.getGameInitializer().subscribe(latch::countDown);

        Thread gameThread =
                new Thread(
                        () -> {
                            try {
                                game.run();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

        gameSettings.setGameOver(false);
        gameThread.start();

        // Wait for the game to initialize
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new AssertionError("Game initialization timed out");
        }

        Thread.sleep(1000);
        double framesAfterOneSecond = game.getFramesCount();
        assertEquals(60, framesAfterOneSecond, 2);

        Thread.sleep(1000);
        double framesAfterTwoSeconds = game.getFramesCount();
        assertEquals(120, framesAfterTwoSeconds, 2);

        Thread.sleep(8000);
        double framesAfterTenSeconds = game.getFramesCount();
        assertEquals(600, framesAfterTenSeconds, 2);

        gameSettings.setGameOver(true);
        gameThread.join();
    }

    @Test
    void verifyGameInstance() {
        assertNotNull(game.getGameInitializer());
        assertNotNull(game.getKeyboardEventManager());
        assertNotNull(game.getMouseEventManager());
        assertNotNull(game.getSoundIRepository());
        assertNotNull(game.getSpriteIRepository());
        assertNotNull(game.getUpdatePublisher());
    }

    static class TestGameSettings extends AGameSettings {
        private boolean gameOver = true;

        @Override
        public boolean isGameOver() {
            return gameOver;
        }

        public void setGameOver(boolean gameOver) {
            this.gameOver = gameOver;
        }
    }
}
