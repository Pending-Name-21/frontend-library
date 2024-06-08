package com.bridge;

import com.bridge.core.exceptions.GameException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.updatehandler.UpdatePublisher;

/**
 * Main game loop class.
 */
public class Game {
    private InputVerifier inputVerifier;
    private AGameSettings gameSettings;
    private UpdatePublisher updatePublisher;

    /**
     * Constructs a Game with the specified input verifier.
     *
     * @param inputVerifier the input verifier to use
     */
    public Game(
            InputVerifier inputVerifier,
            AGameSettings gameSettings,
            UpdatePublisher updatePublisher) {
        this.inputVerifier = inputVerifier;
        this.gameSettings = gameSettings;
        this.updatePublisher = updatePublisher;
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
     * Runs the main game loop.
     */
    public void run() throws GameException {
        while (!gameSettings.isGameOver()) {
            processInput();
            update();
        }
    }
}
