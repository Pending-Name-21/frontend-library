package com.bridge.core.initializerhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

public class NotPossibleToInitializeSubscribersExceptionTest {

    @Test
    public void testDefaultConstructor() {
        NotPossibleToInitializeSubscribersException exception =
                new NotPossibleToInitializeSubscribersException();

        assertEquals("Error initializing subscribers initializers", exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new Throwable("Cause of the exception");
        NotPossibleToInitializeSubscribersException exception =
                new NotPossibleToInitializeSubscribersException(cause);

        assertEquals("Error initializing subscribers initializers", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }
}
