package com.bridge.core.exceptions.updatehandler;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.handlers.LogHandler;
import java.util.logging.Level;

/**
 * Custom exception for errors during update notification.
 */
public class NotPossibleToNotifySubscribersException extends GameException {

    /**
     * Constructs an FetchingEventsException with the specified detail message.
     */
    public NotPossibleToNotifySubscribersException() {
        super("Error notifying subscribers");
        LogHandler.log(this, getLogLevel());
    }

    /**
     * Constructs an NotPossibleToNotifySubscribersException with the
     * specified detail message and cause.
     *
     * @param cause the cause
     */
    public NotPossibleToNotifySubscribersException(Throwable cause) {
        super("Error notifying subscribers", cause);
        LogHandler.log(this, getLogLevel());
    }

    @Override
    public Level getLogLevel() {
        return Level.SEVERE;
    }
}
