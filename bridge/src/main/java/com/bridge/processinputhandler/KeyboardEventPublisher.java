package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.KeyboardEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The KeyboardEventPublisher class implements the IPublisher interface
 * to publish keyboard events to subscribed listeners.
 *
 * <p>This class manages a buffer of keyboard events and notifies all subscribed
 * IKeyboardEventSubscriber instances when new events are published.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Create a buffer for keyboard events
 * ConcurrentLinkedQueue<KeyboardEvent> eventBuffer = new ConcurrentLinkedQueue<>();
 *
 * // Create a publisher and add subscribers
 * KeyboardEventPublisher publisher = new KeyboardEventPublisher(eventBuffer);
 * publisher.subscribe(new MyKeyboardEventSubscriber());
 *
 * // Simulate publishing events
 * eventBuffer.add(new KeyboardEvent(...));
 * publisher.publish();
 * </pre>
 */
public class KeyboardEventPublisher implements IPublisher {
    private final ConcurrentLinkedQueue<KeyboardEvent> buffer;
    private final List<IKeyboardEventSubscriber> subscribers;

    /**
     * Constructs a KeyboardEventPublisher with the specified event buffer.
     *
     * @param buffer the concurrent linked queue used to store keyboard events
     */
    public KeyboardEventPublisher(ConcurrentLinkedQueue<KeyboardEvent> buffer) {
        this.buffer = buffer;
        subscribers = new ArrayList<>();
    }

    /**
     * Subscribes a listener to receive keyboard events.
     *
     * @param subscriber the subscriber implementing IKeyboardEventSubscriber
     */
    public void subscribe(IKeyboardEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Publishes keyboard events to all subscribed listeners.
     * This method iterates through the buffer of keyboard events and notifies
     * each subscriber using their doNotify method.
     * After notifying subscribers, the buffer is cleared.
     */
    @Override
    public void publish() {
        subscribers.forEach(
                sub -> {
                    buffer.forEach(sub::doNotify);
                });
        buffer.clear();
    }
}
