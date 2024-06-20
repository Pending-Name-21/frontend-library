package com.bridge.core.handlers;

import com.bridge.core.exceptions.GameException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles logging for game-related exceptions.
 */
public class LogHandler {
    private static final Logger logger = Logger.getLogger(LogHandler.class.getName());

    /**
     * Logs a GameException at a specified level.
     *
     * @param e the GameException to log
     * @param level the logging level
     */
    public static void log(GameException e, Level level) {
        logger.log(level, e.getMessage(), e);
    }

    /**
     * Logs a message and a GameException at a specified level.
     *
     * @param message the message to log
     * @param e the GameException to log
     * @param level the logging level
     */
    public void log(String message, GameException e, Level level) {
        logger.log(level, message, e);
    }
}
