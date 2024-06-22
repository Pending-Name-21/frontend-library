package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.*;

import CoffeeTime.InputEvents.KeyboardEvent;
import com.bridge.Utils;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.jupiter.api.Test;

public class ReceiverTest {

    @Test
    void verifyDeserialization() {
        Optional<ConcurrentLinkedQueue<KeyboardEvent>> buffer = Utils.createBuffer();
        if (buffer.isEmpty()) {
            fail();
        }

        buffer.get()
                .forEach(
                        event -> {
                            assertEquals("Pressed", event.state());
                            assertEquals("Return", event.name());
                        });
    }
}
