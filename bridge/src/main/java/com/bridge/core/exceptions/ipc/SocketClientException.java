package com.bridge.core.exceptions.ipc;

import com.bridge.core.exceptions.GameException;
import java.util.logging.Level;

public class SocketClientException extends GameException {

    public SocketClientException(String message) {
        super(message);
    }

    public SocketClientException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Level getLogLevel() {
        return Level.SEVERE;
    }
}
