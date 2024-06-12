package com.bridge.core.exceptions.renderHandlerExceptions;

import com.bridge.core.exceptions.GameException;
import java.util.logging.Level;

/**
 * Exception thrown when there is a rendering error.
 */
public class RenderException extends GameException {

    /**
     * Constructs a RenderException with the specified detail message.
     *
     * @param message the detail message
     */
    public RenderException(String message) {
        super(message);
    }

    /**
     * Constructs a RenderException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public RenderException(String message, Throwable cause) {
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
