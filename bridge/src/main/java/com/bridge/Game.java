package com.bridge;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.extras.SplashScreen;
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
    private SplashScreen splashScreen;
    private int framesCount;

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
            GameInitializer gameInitializer)
            throws GameException {
        splashScreen = new SplashScreen(framesCount);
        this.inputVerifier = inputVerifier;
        this.gameSettings = gameSettings;
        this.updatePublisher = updatePublisher;
        this.updatePublisher.subscribe(splashScreen);
        this.renderManager = renderManager;
        this.gameInitializer = gameInitializer;
        this.gameInitializer.subscribe(splashScreen);
    }

    /**
     * Initializes game subscribers initializers.
     */
    public void initialize() throws GameException {
        splashScreen.init();
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
        splashScreen.notifySubscriber();
        splashScreen.setFramesCount(framesCount);
    }

    /**
     * Renders sprites and plays sounds
     */
    public void render() throws RenderException {
        renderManager.render();
    }

    /**
     * Gets the current frames count
     * @return the frames count
     */
    public int getFramesCount() {
        return framesCount;
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
            framesCount++;
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Thread.yield();
        }
    }
}
