package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.Event;
import CoffeeTime.InputEvents.Keyboard;

/**
 * The KeyboardEventManager class extends AEventManager to manage and publish keyboard events
 * to subscribed listeners.
 *
 * <p>This class manages a buffer of keyboard events and notifies all subscribed
 * IEventSubscriber instances when new events are published.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Create an instance of KeyboardEventManager
 * KeyboardEventManager eventManager = new KeyboardEventManager();
 *
 * // Add subscribers to the event manager
 * eventManager.subscribe(new MyKeyboardEventSubscriber());
 *
 * // Simulate feeding and publishing events
 * Event event = new Event(...);
 * eventManager.feed(event);
 * eventManager.publish();
 * </pre>
 */
public class KeyboardEventManager extends AEventManager<Keyboard> {

    /**
     * Constructs a KeyboardEventManager.
     * Initializes the event buffer and subscriber list.
     */
    public KeyboardEventManager() {
        super();
    }

    /**
     * Subscribes a listener to receive keyboard events.
     *
     * @param subscriber the subscriber implementing IEventSubscriber<Keyboard>
     */
    @Override
    public void subscribe(IEventSubscriber<Keyboard> subscriber) {
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
        subscribers.forEach(subscriber -> events.forEach(subscriber::doNotify));
        events.clear();
    }

    /**
     * Adds a new event to the buffer.
     *
     * @param event the event containing the keyboard event to be added
     */
    @Override
    public void feed(Event event) {
        events.add(event.keyboard());
    }
}
