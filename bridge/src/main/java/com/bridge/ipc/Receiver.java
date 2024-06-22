package com.bridge.ipc;

import CoffeeTime.InputEvents.KeyboardEvent;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver {
    private final ConcurrentLinkedQueue<KeyboardEvent> keyboardEvents;

    public Receiver(ConcurrentLinkedQueue<KeyboardEvent> keyboardEvents) {
        this.keyboardEvents = keyboardEvents;
    }

    public void handleMessage(ByteBuffer buffer) {
        KeyboardEvent keyboardEvent = KeyboardEvent.getRootAsKeyboardEvent(buffer);
        System.out.printf("Got message: %s\n", keyboardEvent.name());
        keyboardEvents.add(keyboardEvent);
    }
}
