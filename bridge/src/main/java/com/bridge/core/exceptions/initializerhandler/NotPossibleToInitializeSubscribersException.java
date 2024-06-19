package com.bridge.core.exceptions.initializerhandler;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.handlers.LogHandler;
import java.util.logging.Level;

/**
 * Custom exception thrown when an error occurs during the initialization of subscribers.
 */
public class NotPossibleToInitializeSubscribersException extends GameException {

    /**
     * Constructs an NotPossibleToInitializeSubscribersException with the specified detail message.
     */
    public NotPossibleToInitializeSubscribersException() {
        super("Error initializing subscribers initializers");
        LogHandler.log(this, getLogLevel());
    }

    /**
     * Constructs an NotPossibleToInitializeSubscribersException with the
     * specified detail message and cause.
     *
     * @param cause the cause
     */
    public NotPossibleToInitializeSubscribersException(Throwable cause) {
        super("Error initializing subscribers initializers", cause);
        LogHandler.log(this, getLogLevel());
    }

    @Override
    public Level getLogLevel() {
        return Level.SEVERE;
    }
}
