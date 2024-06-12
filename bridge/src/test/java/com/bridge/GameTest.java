package com.bridge;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.processinputhandler.NullInputListenersException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.ProcessInputPublisher;
import com.bridge.processinputhandler.listeners.InputListener;
import com.bridge.updatehandler.UpdatePublisher;

import java.beans.Transient;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private TestInputVerifier inputVerifier;
    private TestGameSettings gameSettings;
    private TestUpdatePublisher updatePublisher;
    private TestRenderManager renderManager;
    private Game game;

    @BeforeEach
    void setUp() throws NullInputListenersException {
        inputVerifier = new TestInputVerifier();
        gameSettings = new TestGameSettings();
        updatePublisher = new TestUpdatePublisher();
        renderManager = new TestRenderManager();
        game = new Game(inputVerifier, gameSettings, updatePublisher, renderManager);
    }

    @Test
    void testUpdate() throws GameException {
        game.update();
        assertTrue(updatePublisher.notified);
    }

    @Test
    void testRender() {
        game.render();
        assertTrue(renderManager.spritesRendered);
        assertTrue(renderManager.soundsPlayed);
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

        assertTrue(inputVerifier.checked);
        assertTrue(updatePublisher.notified);
        assertTrue(renderManager.spritesRendered);
        assertTrue(renderManager.soundsPlayed);
    }

    static class TestInputVerifier extends InputVerifier {
        boolean checked = false;

        TestInputVerifier() throws NullInputListenersException {
            super(new TestProcessInputPublisher(), List.of(new TestInputListener()));
        }

        @Override
        public void check() {
            checked = true;
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

    static class TestUpdatePublisher extends UpdatePublisher {
        boolean notified = false;

        @Override
        public void notifySubscribers() {
            notified = true;
        }
    }

    static class TestProcessInputPublisher extends ProcessInputPublisher {
        boolean notified = false;

        @Override
        public void notifySubscribers(com.bridge.processinputhandler.EventType event) {
            notified = true;
        }
    }

    static class TestInputListener implements InputListener {
        @Override
        public List<String> listen() {
            return List.of("testEvent");
        }
    }

    static class TestRenderManager extends RenderManager {
        boolean spritesRendered = false;
        boolean soundsPlayed = false;

        @Override
        public void renderSprites() {
            spritesRendered = true;
        }

        @Override
        public void playSounds() {
            soundsPlayed = true;
        }
    }
}
