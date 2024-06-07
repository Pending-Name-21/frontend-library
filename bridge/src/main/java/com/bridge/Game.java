package com.bridge;

import com.bridge.gamesettings.AGameSettings;
import com.bridge.processinputhandler.InputVerifier;

/**
 * Main game loop class.
 */
public class Game {
    private InputVerifier inputVerifier;
    private AGameSettings gameSettings;

    /**
     * Constructs a Game with the specified input verifier.
     *
     * @param inputVerifier the input verifier to use
     */
    public Game(InputVerifier inputVerifier, AGameSettings gameSettings) {
        this.inputVerifier = inputVerifier;
        this.gameSettings = gameSettings;
    }

    /**
     * Processes input by calling the check method on the input verifier.
     */
    private void processInput() {
        inputVerifier.check();
    }

    /**
     * Runs the main game loop.
     */
    public void run() {
        while (!gameSettings.isGameOver()) {
            processInput();
        }
    }
}