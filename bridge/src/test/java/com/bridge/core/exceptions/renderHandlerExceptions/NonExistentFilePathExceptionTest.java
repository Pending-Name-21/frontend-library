package com.bridge.core.exceptions.renderHandlerExceptions;

import com.bridge.core.exceptions.GameException;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class NonExistentFilePathExceptionTest {

    @Test
    void testExceptionMessage() {
        String filePath = "non_existing_file.txt";
        NonExistentFilePathException exception = new NonExistentFilePathException(filePath);

        assertEquals("File does not exist: " + filePath, exception.getMessage());
    }

    @Test
    void testLogLevel() {
        String filePath = "non_existing_file.txt";
        NonExistentFilePathException exception = new NonExistentFilePathException(filePath);

        assertEquals(Level.SEVERE, exception.getLogLevel());
    }

    @Test
    void testInstanceOfGameException() {
        String filePath = "non_existing_file.txt";
        NonExistentFilePathException exception = new NonExistentFilePathException(filePath);

        assertTrue(exception instanceof GameException);
    }
}

