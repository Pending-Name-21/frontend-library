package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.Event;
import CoffeeTime.InputEvents.Mouse;
import com.bridge.core.handlers.LogHandler;
import java.util.logging.Level;

/**
 * The MouseEventManager class extends AEventManager to manage and publish mouse events
 * to subscribed listeners.
 *
 * <p>This class manages a buffer of mouse events and notifies all subscribed
 * IEventSubscriber instances when new events are published.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Create an instance of MouseEventManager
 * MouseEventManager eventManager = new MouseEventManager();
 *
 * // Add subscribers to the event manager
 * eventManager.subscribe(new MyMouseEventSubscriber());
 *
 * // Simulate feeding and publishing events
 * Event event = new Event(...);
 * eventManager.feed(event);
 * eventManager.publish();
 * </pre>
 */
public class MouseEventManager extends AEventManager<Mouse> {

    /**
     * Constructs a MouseEventManager.
     * Initializes the event buffer and subscriber list.
     */
    public MouseEventManager() {
        super();
    }

    /**
     * Subscribes a listener to receive mouse events.
     *
     * @param subscriber the subscriber implementing IEventSubscriber<Mouse>
     */
    @Override
    public void subscribe(IEventSubscriber<Mouse> subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Publishes mouse events to all subscribed listeners.
     * This method iterates through the buffer of mouse events and notifies
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
     * @param event the event containing the mouse event to be added
     */
    @Override
    public void feed(Event event) {
        Mouse mouse = event.mouse();
        if (mouse != null) {
            events.add(mouse);
        } else {
            LogHandler.log(Level.WARNING, "Received mouse event is null");
        }
    }
}
