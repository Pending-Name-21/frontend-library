package com.bridge.core.processinputhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.bridge.core.exceptions.processinputhandler.NullInputListenersException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

public class NullInputListenersExceptionTest {

    @Test
    public void testDefaultConstructor() {
        NullInputListenersException exception = new NullInputListenersException();

        assertEquals("Input listeners cannot be null", exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new Throwable("Cause of the exception");
        NullInputListenersException exception = new NullInputListenersException(cause);

        assertEquals("Input listeners cannot be null", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }
}
