package com.bridge.processinputhandler;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.jupiter.api.Test;

class KeyboardEventPublisherTest {

    @Test
    void verifyEmptyBufferNotificationTest() {
        KeyboardEventPublisher publisher =
                new KeyboardEventPublisher(new ConcurrentLinkedQueue<>());
        IKeyboardEventSubscriber subscriber = event -> fail();
        publisher.subscribe(subscriber);
        publisher.publish();
    }
}
