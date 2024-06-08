package com.bridge.core.exceptions;

import java.util.logging.Level;

/**
 * Abstract class for game-related exceptions.
 */
public abstract class GameException extends Exception {

    /**
     * Constructs a GameException with the specified detail message.
     *
     * @param message the detail message
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Constructs a GameException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns the logging level associated with the exception.
     *
     * @return the logging level
     */
    public abstract Level getLogLevel();
}
