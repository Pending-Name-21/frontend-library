package com.bridge.processinputhandler;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.fail;

class KeyboardEventPublisherTest {

    @Test
    void verifyEmptyBufferNotificationTest() {
        KeyboardEventPublisher publisher = new KeyboardEventPublisher(new ConcurrentLinkedQueue<>());
        IKeyboardEventSubscriber subscriber = event -> fail();
        publisher.subscribe(subscriber);
        publisher.publish();
    }
}
