package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.KeyboardEvent;
import com.bridge.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;

class InputVerifierTest {

    @Test
    void verifyEventPublication() {
        Optional<ConcurrentLinkedQueue<KeyboardEvent>> buffer = Utils.createBuffer();
        if (buffer.isEmpty()) {
            fail("The buffer is empty");
        }

        KeyboardEventPublisher publisher = new KeyboardEventPublisher(buffer.get());
        List<IPublisher> publishers = List.of(publisher);
        IKeyboardEventSubscriber subscriber = Assertions::assertNotNull; // should verify subscriber is notified
        publisher.subscribe(subscriber);

        InputVerifier inputVerifier = new InputVerifier(publishers);
        inputVerifier.check();
    }
}
