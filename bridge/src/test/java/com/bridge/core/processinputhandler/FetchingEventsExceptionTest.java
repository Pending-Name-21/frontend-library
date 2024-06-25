package com.bridge.core.processinputhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.bridge.core.exceptions.processinputhandler.FetchingEventsException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

public class FetchingEventsExceptionTest {

    @Test
    public void testDefaultConstructor() {
        FetchingEventsException exception = new FetchingEventsException();

        assertEquals("Error fetching required events listening", exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(Level.WARNING, exception.getLogLevel());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new Throwable("Cause of the exception");
        FetchingEventsException exception = new FetchingEventsException(cause);

        assertEquals("Error fetching required events listening", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertEquals(Level.WARNING, exception.getLogLevel());
    }
}
