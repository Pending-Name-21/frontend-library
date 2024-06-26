package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.Event;
import CoffeeTime.InputEvents.Keyboard;
import com.bridge.EventGenerator;
import com.bridge.ipc.Receiver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AEventManagerTest {

    @Test
    void verifyEmptyBufferNotificationTest() {
        AEventManager<Keyboard> eventManager = new AEventManager<>() {
            @Override
            public void feed(Event event) {
                assertNotNull(event.keyboard());
            }

            @Override
            public void publish() {
            }

            @Override
            public void subscribe(IEventSubscriber<Keyboard> subscriber) {
                subscribers.add(subscriber);
            }
        };

        eventManager.subscribe(event -> {
            assertEquals("KeyPressed", event.type());
            assertEquals("Return", event.type());
        });

        Receiver receiver = new Receiver();
        receiver.addBuffer(eventManager);
        receiver.handleMessage(new EventGenerator().makeEvent("", "", "KeyPressed", "Return"));
        eventManager.publish();
    }
}
