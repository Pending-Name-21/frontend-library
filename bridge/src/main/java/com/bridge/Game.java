package com.bridge;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.updatehandler.UpdatePublisher;

/**
 * Main game loop class.
 */
public class Game {
    private InputVerifier inputVerifier;
    private AGameSettings gameSettings;
    private UpdatePublisher updatePublisher;
    private RenderManager renderManager;
    private GameInitializer gameInitializer;

    /**
     * Constructs a Game with the specified input verifier.
     *
     * @param inputVerifier the input verifier to handle input events
     * @param gameSettings the game settings to use
     * @param updatePublisher the update publisher to handle updates notifiers
     * @param renderManager the render manager to manage sprites and sound
     * @param gameInitializer the game initializer to handle game initializers
     */
    public Game(
            InputVerifier inputVerifier,
            AGameSettings gameSettings,
            UpdatePublisher updatePublisher,
            RenderManager renderManager,
            GameInitializer gameInitializer) {
        this.inputVerifier = inputVerifier;
        this.gameSettings = gameSettings;
        this.updatePublisher = updatePublisher;
        this.renderManager = renderManager;
        this.gameInitializer = gameInitializer;
    }

    /**
     * Initializes game subscribers initializers.
     */
    public void initialize() throws GameException {
        gameInitializer.initializeSubscribers();
    }

    /**
     * Processes input by calling the check method on the input verifier.
     */
    private void processInput() throws GameException {
        inputVerifier.check();
    }

    /**
     * Updates the game state and notifies subscribers.
     */
    public void update() throws GameException {
        updatePublisher.notifySubscribers();
    }

    /**
     * Renders sprites and plays sounds
     */
    public void render() throws RenderException {
        renderManager.render();
    }

    /**
     * Runs the main game loop.
     */
    public void run() throws GameException {
        initialize();
        while (!gameSettings.isGameOver()) {
            processInput();
            update();
            render();
            Thread.yield();
        }
    }
}
