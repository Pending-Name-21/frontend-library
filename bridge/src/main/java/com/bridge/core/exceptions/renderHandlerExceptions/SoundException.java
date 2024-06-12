package com.bridge.core.exceptions.renderHandlerExceptions;

import com.bridge.core.exceptions.GameException;
import java.util.logging.Level;

public class SoundException extends GameException {

    /**
     * Constructs a SoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public SoundException(String message) {
        super(message);
    }

    /**
     * Constructs a SoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public SoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns the logging level associated with the exception.
     *
     * @return the logging level
     */
    @Override
    public Level getLogLevel() {
        return Level.SEVERE;
    }
}
