package com.bridge.game;

import com.bridge.processinputhandler.InputVerifier;

/**
 * Main game loop class.
 */
public class Game {
    private InputVerifier inputVerifier;

    /**
     * Constructs a Game with the specified input verifier.
     *
     * @param inputVerifier the input verifier to use
     */
    public Game(InputVerifier inputVerifier) {
        this.inputVerifier = inputVerifier;
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
        while (true) {
            processInput();
        }
    }
}