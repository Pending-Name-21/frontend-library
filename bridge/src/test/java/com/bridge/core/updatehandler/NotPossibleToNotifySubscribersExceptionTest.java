package com.bridge.core.updatehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.bridge.core.exceptions.updatehandler.NotPossibleToNotifySubscribersException;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

public class NotPossibleToNotifySubscribersExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        NotPossibleToNotifySubscribersException exception =
                new NotPossibleToNotifySubscribersException();

        assertEquals("Error notifying subscribers", exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        Throwable cause = new Throwable("Cause of the exception");
        NotPossibleToNotifySubscribersException exception =
                new NotPossibleToNotifySubscribersException(cause);

        assertEquals("Error notifying subscribers", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertEquals(Level.SEVERE, exception.getLogLevel());
    }
}
