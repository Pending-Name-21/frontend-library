package com.bridge.core.exceptions;

import java.util.logging.Level;

public class SocketServerException extends GameException {
    public SocketServerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Level getLogLevel() {
        return Level.SEVERE;
    }
}
