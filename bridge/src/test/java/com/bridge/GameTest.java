package com.bridge;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import com.bridge.core.exceptions.processinputhandler.FetchingEventsException;
import com.bridge.core.exceptions.processinputhandler.NullInputListenersException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.ProcessInputPublisher;
import com.bridge.processinputhandler.listeners.InputListener;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.updatehandler.UpdatePublisher;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private TestInputVerifier inputVerifier;
    private TestGameSettings gameSettings;
    private TestUpdatePublisher updatePublisher;
    private RenderManager renderManager;
    private TestGameInitializer gameInitializer;
    private Game game;

    @BeforeEach
    void setUp() throws GameException {
        inputVerifier = new TestInputVerifier();
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
    }

    static class TestInputVerifier extends InputVerifier {
        boolean checked = false;

        TestInputVerifier() throws NullInputListenersException {
            super(new TestProcessInputPublisher(), List.of(new TestInputListener()));
        }

        @Override
        public void check() throws FetchingEventsException {
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

    static class TestGameInitializer extends GameInitializer {
        boolean notified = false;
        public boolean throwException = false;

        @Override
        public void initializeSubscribers() throws NotPossibleToInitializeSubscribersException {
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
}
