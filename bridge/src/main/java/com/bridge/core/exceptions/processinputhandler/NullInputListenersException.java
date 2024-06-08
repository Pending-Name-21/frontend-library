package com.bridge.core.exceptions.processinputhandler;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.handlers.LogHandler;
import java.util.logging.Level;

/**
 * Custom exception for when there are no input listeners available.
 */
public class NullInputListenersException extends GameException {

    /**
     * Constructs an NullInputListenersException with the specified detail message.
     */
    public NullInputListenersException() {
        super("Input listeners cannot be null");
        LogHandler.log(this, getLogLevel());
    }

    /**
     * Constructs an NullInputListenersException with the specified detail message and cause.
     *
     * @param cause the cause
     */
    public NullInputListenersException(Throwable cause) {
        super("Input listeners cannot be null", cause);
        LogHandler.log(this, getLogLevel());
    }

    @Override
    public Level getLogLevel() {
        return Level.SEVERE;
    }
}
