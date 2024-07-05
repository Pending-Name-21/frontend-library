package com.bridge;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.ipc.SocketClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameWithExceptionsTest {
    private TestGameSettings gameSettings;
    private Game game;
    private TestGameInitializer gameInitializer;
    private static Thread SERVER_THREAD;

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
    void setUp() throws GameException {
        gameSettings = new TestGameSettings();
        gameInitializer = new TestGameInitializer();
        game = new Game(gameSettings);
    }

    @Test
    public void testInitializeWithException() {
        gameInitializer.subscribe(null);
        gameInitializer.subscribe(null);
        gameInitializer.subscribe(null);
        gameInitializer.subscribe(null);

        try {
            game.initialize();
        } catch (Exception e) {
            assertThrows(NotPossibleToInitializeSubscribersException.class, game::run);
        }
    }

    @Test
    void testRunWithGameException() {
        gameSettings.setGameOver(false);
        gameInitializer.subscribe(null);

        Thread gameThread =
                new Thread(
                        () -> {
                            try {
                                game.run();
                                fail("GameException was expected");
                            } catch (GameException e) {
                                assertInstanceOf(GameException.class, e);
                                assertThrows(GameException.class, game::run);
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
                                fail("InterruptedException occurred: " + e.getMessage());
                            }
                        });

        stopperThread.start();
        try {
            stopperThread.join();
            gameThread.join();
        } catch (InterruptedException e) {
            fail("InterruptedException occurred: " + e.getMessage());
        }
    }

    static class TestGameSettings extends AGameSettings {
        private boolean gameOver = true;

        public void setGameOver(boolean gameOver) {
            this.gameOver = gameOver;
        }

        @Override
        public boolean isGameOver() {
            return gameOver;
        }
    }

    static class TestGameInitializer extends GameInitializer {
        public boolean throwException = false;
        boolean notified = false;

        @Override
        public void initializeSubscribers() throws NotPossibleToInitializeSubscribersException {
            notified = true;
        }
    }
}
