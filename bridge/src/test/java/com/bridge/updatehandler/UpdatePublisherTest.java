package com.bridge.updatehandler;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.updatehandler.NotPossibleToNotifySubscribersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdatePublisherTest {
    private UpdatePublisher updatePublisher;
    private TestSubscriber subscriber1;
    private TestSubscriber subscriber2;

    @BeforeEach
    void setUp() {
        updatePublisher = new UpdatePublisher();
        subscriber1 = new TestSubscriber();
        subscriber2 = new TestSubscriber();
    }

    @Test
    void testNotifySubscribers() throws NotPossibleToNotifySubscribersException {
        updatePublisher.subscribe(subscriber1);
        updatePublisher.subscribe(subscriber2);

        updatePublisher.notifySubscribers();

        assertTrue(subscriber1.notified);
        assertTrue(subscriber2.notified);
    }

    static class TestSubscriber implements IUpdateSubscriber {
        boolean notified = false;

        @Override
        public void notifySubscriber() {
            notified = true;
        }
    }
}
