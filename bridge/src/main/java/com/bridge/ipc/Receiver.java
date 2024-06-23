package com.bridge.ipc;

import CoffeeTime.InputEvents.KeyboardEvent;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@code Receiver} class is responsible for handling and processing incoming keyboard events.
 * It receives messages in the form of a {@link ByteBuffer}, converts them to {@link KeyboardEvent} objects,
 * and stores them in a thread-safe queue.
 */
public class Receiver {

    /**
     * A thread-safe queue to store keyboard events.
     */
    private final ConcurrentLinkedQueue<KeyboardEvent> keyboardEvents;

    /**
     * Constructs a {@code Receiver} with the specified queue to store keyboard events.
     *
     * @param keyboardEvents the queue to store the keyboard events
     */
    public Receiver(ConcurrentLinkedQueue<KeyboardEvent> keyboardEvents) {
        this.keyboardEvents = keyboardEvents;
    }

    /**
     * Processes the incoming message buffer, converts it to a {@link KeyboardEvent},
     * and adds it to the queue of keyboard events.
     *
     * @param buffer the message buffer containing the keyboard event data
     */
    public void handleMessage(ByteBuffer buffer) {
        KeyboardEvent keyboardEvent = KeyboardEvent.getRootAsKeyboardEvent(buffer);
        System.out.printf("Got message: %s\n", keyboardEvent.name());
        keyboardEvents.add(keyboardEvent);
    }
}
