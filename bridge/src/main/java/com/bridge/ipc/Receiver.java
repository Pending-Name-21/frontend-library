package com.bridge.ipc;

import CoffeeTime.InputEvents.Event;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Receiver} class is responsible for handling and processing incoming keyboard events.
 * It receives messages in the form of a {@link ByteBuffer}, converts them to {@link Event} objects,
 * and stores them in a thread-safe queue.
 */
public class Receiver {

    /**
     * A thread-safe queue to store keyboard events.
     */
    private final List<IEventBuffer> eventBuffers;

    public Receiver() {
        eventBuffers = new ArrayList<>();
    }

    public void handleMessage(ByteBuffer message) {
        Event event = Event.getRootAsEvent(message);
        eventBuffers.forEach(buffer -> buffer.feed(event));
    }

    public void addBuffer(IEventBuffer buffer) {
        eventBuffers.add(buffer);
    }
}
