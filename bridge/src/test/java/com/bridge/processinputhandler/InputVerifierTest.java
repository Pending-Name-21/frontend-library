package com.bridge.processinputhandler;

import static org.junit.jupiter.api.Assertions.*;

import CoffeeTime.InputEvents.KeyboardEvent;
import com.bridge.Utils;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InputVerifierTest {

    @Test
    void verifyEventPublication() {
        Optional<ConcurrentLinkedQueue<KeyboardEvent>> buffer = Utils.createBuffer();
        if (buffer.isEmpty()) {
            fail("The buffer is empty");
        }

        KeyboardEventPublisher publisher = new KeyboardEventPublisher(buffer.get());
        List<IPublisher> publishers = List.of(publisher);
        IKeyboardEventSubscriber subscriber =
                Assertions::assertNotNull; // should verify subscriber is notified
        publisher.subscribe(subscriber);

        InputVerifier inputVerifier = new InputVerifier(publishers);
        inputVerifier.check();
    }
}
