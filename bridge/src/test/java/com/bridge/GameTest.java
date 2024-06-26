package com.bridge;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.KeyboardEventManager;
import com.bridge.processinputhandler.MouseEventManager;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.updatehandler.UpdatePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class GameTest {
    private InputVerifier inputVerifier;
    private TestGameSettings gameSettings;
    private TestUpdatePublisher updatePublisher;
    private RenderManager renderManager;
    private TestGameInitializer gameInitializer;
    private Game game;

    @BeforeEach
    void setUp() throws GameException {
        inputVerifier = new InputVerifier(List.of(new KeyboardEventManager(), new MouseEventManager()));
        gameSettings = new TestGameSettings();
        updatePublisher = new TestUpdatePublisher();
        renderManager = new RenderManager();
        gameInitializer = new TestGameInitializer();
        game =
                new Game(
                        inputVerifier,
                        gameSettings,
                        updatePublisher,
                        renderManager,
                        gameInitializer);
    }

    @Test
    void testInitialize() throws GameException {
        game.initialize();
        assertTrue(gameInitializer.notified);
    }

    @Test
    void testUpdate() throws GameException {
        game.update();
        assertTrue(updatePublisher.notified);
    }

    @Test
    void testRender() {
        game.render();
    }

    @Test
    void testRun() throws GameException, InterruptedException {
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
                                System.out.println("STOPPER THREAD");
                            } catch (InterruptedException e) {
                                fail("InterruptedException occurred: " + e.getMessage());
                            }
                        });

        stopperThread.start();
        stopperThread.join();
        gameThread.join();

        assertTrue(updatePublisher.notified);
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

    static class TestUpdatePublisher extends UpdatePublisher {
        boolean notified = false;

        @Override
        public void notifySubscribers() {
            notified = true;
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
