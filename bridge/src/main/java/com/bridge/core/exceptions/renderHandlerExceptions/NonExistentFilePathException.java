package com.bridge.core.exceptions.renderHandlerExceptions;

import com.bridge.core.exceptions.GameException;

import java.util.logging.Level;

public class NonExistentFilePathException extends GameException {

    /**
     * Constructs a NonExistentFilePath with the specified detail message.
     *
     * @param filePath the failed loading file path
     */
    public NonExistentFilePathException(String filePath) {
        super("File does not exist: " + filePath);
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
