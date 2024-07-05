package com.bridge;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.ipc.TransmitterTest;
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
        SERVER_THREAD = TransmitterTest.startServer();
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
        TransmitterTest.cleanup();
    }

    @BeforeEach
    void setUp() throws NonExistentFilePathException {
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
        try {
            game.run();
        } catch (GameException e) {
            fail("GameException occurred: " + e.getMessage());
        }
        /*Thread gameThread =
                new Thread(
                        () -> {

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
        gameThread.join();*/
    }

    @Test
    public void testFramesCount() throws Exception {
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

    @Test
    void verifyGameInstance() throws NonExistentFilePathException {
        Game game =
                new Game(
                        new AGameSettings() {
                            @Override
                            public boolean isGameOver() {
                                return false;
                            }
                        });

        assertNotNull(game.getGameInitializer());
        assertNotNull(game.getKeyboardEventManager());
        assertNotNull(game.getMouseEventManager());
        assertNotNull(game.getSoundIRepository());
        assertNotNull(game.getSpriteIRepository());
        assertNotNull(game.getUpdatePublisher());
    }
}
