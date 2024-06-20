package com.bridge.processinputhandler;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.processinputhandler.FetchingEventsException;
import com.bridge.core.exceptions.processinputhandler.NullInputListenersException;
import com.bridge.processinputhandler.listeners.InputListener;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputVerifierTest {
    private TestProcessInputPublisher processInputPublisher;
    private TestInputListener inputListener;
    private InputVerifier inputVerifier;

    @BeforeEach
    void setUp() throws NullInputListenersException {
        processInputPublisher = new TestProcessInputPublisher();
        inputListener = new TestInputListener();
        inputVerifier = new InputVerifier(processInputPublisher, List.of(inputListener));
    }

    @Test
    void testCheck() throws FetchingEventsException {
        inputListener.events = List.of("event1");

        inputVerifier.check();

        assertTrue(processInputPublisher.notified);
    }

    static class TestProcessInputPublisher extends ProcessInputPublisher {
        boolean notified = false;

        @Override
        public void notifySubscribers(EventType event) {
            notified = true;
        }
    }

    static class TestInputListener implements InputListener {
        List<String> events = List.of("testEvent");

        @Override
        public List<String> listen() {
            return events;
        }
    }
}
