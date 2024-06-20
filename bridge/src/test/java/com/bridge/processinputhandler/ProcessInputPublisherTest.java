package com.bridge.processinputhandler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessInputPublisherTest {
    private ProcessInputPublisher processInputPublisher;
    private TestSubscriber subscriber;
    private EventType eventType;

    @BeforeEach
    void setUp() {
        processInputPublisher = new ProcessInputPublisher();
        subscriber = new TestSubscriber();
        eventType = new EventType("event");
    }

    @Test
    void testNotifySubscribers() {
        processInputPublisher.subscribe(eventType, subscriber);

        processInputPublisher.notifySubscribers(eventType);

        assertTrue(subscriber.notified);
    }

    static class TestSubscriber implements IProcessInputSubscriber {
        boolean notified = false;

        @Override
        public void notify(EventType event) {
            notified = true;
        }
    }
}
