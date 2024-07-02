package com.bridge.core.renderhandlerexceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

public class RenderExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String message = "Rendering error occurred";
        RenderException exception = new RenderException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Rendering error occurred";
        Throwable cause = new Throwable("Cause of the exception");
        RenderException exception = new RenderException(message, cause);

        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }
}
