package com.bridge.processinputhandler;

/**
 * The IPublisher interface provides a method for publishing events or messages
 * to subscribers. Implementing classes should define the logic for notifying
 * subscribers when an event occurs.
 *
 * <p>This interface is typically used in a publisher-subscriber pattern where
 * the publisher is responsible for sending notifications to its subscribers.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * public class MyEventPublisher implements IPublisher {
 *     private List<IKeyboardEventSubscriber> subscribers = new ArrayList<>();
 *
 *     public void addSubscriber(IKeyboardEventSubscriber subscriber) {
 *         subscribers.add(subscriber);
 *     }
 *
 *     {@literal @}Override
 *     public void publish() {
 *         // Create a new keyboard event
 *         KeyboardEvent event = new KeyboardEvent(...);
 *
 *         // Notify all subscribers
 *         for (IKeyboardEventSubscriber subscriber : subscribers) {
 *             subscriber.doNotify(event);
 *         }
 *     }
 * }
 * </pre>
 */
public interface IPublisher {

    /**
     * Called to publish an event or message to all subscribers.
     * Implementing classes should define the actions to be taken
     * to notify subscribers when an event occurs.
     */
    void publish();
}
