package com.bridge.core.exceptions.processinputhandler;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.handlers.LogHandler;
import java.util.logging.Level;

/**
 * Custom exception for input verification errors.
 */
public class FetchingEventsException extends GameException {

    /**
     * Constructs an FetchingEventsException with the specified detail message.
     */
    public FetchingEventsException() {
        super("Error fetching required events listening");
        LogHandler.log(this, getLogLevel());
    }

    /**
     * Constructs an FetchingEventsException with the specified detail message and cause.
     * @param cause the cause
     */
    public FetchingEventsException(Throwable cause) {
        super("Error fetching required events listening", cause);
        LogHandler.log(this, getLogLevel());
    }

    @Override
    public Level getLogLevel() {
        return Level.WARNING;
    }
}
