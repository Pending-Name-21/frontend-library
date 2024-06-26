package com.bridge.core.exceptions.renderHandlerExceptions;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.GameException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

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
