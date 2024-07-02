package com.bridge.core.loghandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import com.bridge.core.exceptions.updatehandler.NotPossibleToNotifySubscribersException;
import com.bridge.core.handlers.LogHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogHandlerTest {

    private TestHandler testHandler;
    private Logger logger;
    private LogHandler logHandler;

    @BeforeEach
    public void setUp() {
        logger = Logger.getLogger(LogHandler.class.getName());
        testHandler = new TestHandler();
        logger.addHandler(testHandler);
        logHandler = new LogHandler();
    }

    @Test
    public void testLogWithGameExceptionAndLevel() {
        GameException exception = new NotPossibleToNotifySubscribersException();
        Level level = Level.SEVERE;

        LogHandler.log(exception, level);

        List<LogRecord> logRecords = testHandler.getLogRecords();
        assertEquals(2, logRecords.size());

        LogRecord loggedRecord = logRecords.get(0);
        assertEquals("Error notifying subscribers", loggedRecord.getMessage());
        assertEquals(exception, loggedRecord.getThrown());
        assertEquals(level, loggedRecord.getLevel());
    }

    @Test
    public void testLogWithMessageGameExceptionAndLevel() {
        String message = "Error initializing subscribers initializers";
        GameException exception = new NotPossibleToInitializeSubscribersException();
        Level level = Level.SEVERE;

        logHandler.log(message, exception, level);

        List<LogRecord> logRecords = testHandler.getLogRecords();
        assertEquals(2, logRecords.size());

        LogRecord loggedRecord = logRecords.get(0);
        assertEquals(message, loggedRecord.getMessage());
        assertEquals(exception, loggedRecord.getThrown());
        assertEquals(level, loggedRecord.getLevel());
    }

    private static class TestHandler extends ConsoleHandler {
        private final List<LogRecord> logRecords = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            super.publish(record);
            logRecords.add(record);
        }

        public List<LogRecord> getLogRecords() {
            return logRecords;
        }
    }
}
