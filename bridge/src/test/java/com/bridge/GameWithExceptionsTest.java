package com.bridge;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import com.bridge.core.exceptions.processinputhandler.NullInputListenersException;
import com.bridge.core.exceptions.updatehandler.NotPossibleToNotifySubscribersException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.ProcessInputPublisher;
import com.bridge.processinputhandler.listeners.InputListener;
import com.bridge.processinputhandler.listeners.MouseListener;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.updatehandler.UpdatePublisher;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameWithExceptionsTest {
    private ProcessInputPublisher processInputPublisher;
    private InputVerifier inputVerifier;
    private TestGameSettings gameSettings;
    private UpdatePublisher updatePublisher;
    private RenderManager renderManager;
    private GameInitializer gameInitializer;
    private List<InputListener> inputListeners;
    private Game game;

    @BeforeEach
    void setUp() throws GameException {
        processInputPublisher = new ProcessInputPublisher();
        inputListeners = new ArrayList<>(List.of(new MouseListener()));
        inputVerifier = new InputVerifier(processInputPublisher, inputListeners);
        gameSettings = new TestGameSettings();
        updatePublisher = new UpdatePublisher();
        renderManager = new RenderManager();
        gameInitializer = new GameInitializer();
        game =
                new Game(
                        inputVerifier,
                        gameSettings,
                        updatePublisher,
                        renderManager,
                        gameInitializer);
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
    public void testUpdateWithException() {
        updatePublisher.subscribe(null);
        updatePublisher.subscribe(null);
        updatePublisher.subscribe(null);
        updatePublisher.subscribe(null);

        try {
            game.initialize();
        } catch (Exception e) {
            assertThrows(NotPossibleToNotifySubscribersException.class, game::run);
        }
    }

    @Test
    public void testInstanceInputVerifierWithException() {
        try {
            inputVerifier = new InputVerifier(processInputPublisher, null);
            assertThrows(NullInputListenersException.class, game::run);
        } catch (Exception e) {
        }
    }

    @Test
    void testRunWithGameException() {
        gameSettings.setGameOver(false);
        updatePublisher.subscribe(null);
        gameInitializer.subscribe(null);

        Thread gameThread =
                new Thread(
                        () -> {
                            try {
                                game.run();
                                fail("GameException was expected");
                            } catch (GameException e) {
                                assertTrue(e instanceof GameException);
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
}
