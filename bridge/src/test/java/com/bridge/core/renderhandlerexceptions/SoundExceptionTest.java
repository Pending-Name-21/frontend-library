package com.bridge.core.renderhandlerexceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.bridge.core.exceptions.renderHandlerExceptions.SoundException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

public class SoundExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String message = "Sound error occurred";
        SoundException exception = new SoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Sound error occurred";
        Throwable cause = new Throwable("Cause of the exception");
        SoundException exception = new SoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }
}
