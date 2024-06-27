package com.bridge.processinputhandler;

/**
 * The IPublisher interface provides methods for publishing events or messages
 * to subscribers. Implementing classes should define the logic for notifying
 * subscribers when an event occurs and managing the list of subscribers.
 *
 * <p>This interface is typically used in a publisher-subscriber pattern where
 * the publisher is responsible for sending notifications to its subscribers.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * public class MyEventPublisher implements IPublisher<KeyboardEvent> {
 *     private List<IEventSubscriber<KeyboardEvent>> subscribers = new ArrayList<>();
 *
 *     public void addSubscriber(IEventSubscriber<KeyboardEvent> subscriber) {
 *         subscribers.add(subscriber);
 *     }
 *
 *     {@literal @}Override
 *     public void publish() {
 *         // Create a new keyboard event
 *         KeyboardEvent event = new KeyboardEvent(...);
 *
 *         // Notify all subscribers
 *         for (IEventSubscriber<KeyboardEvent> subscriber : subscribers) {
 *             subscriber.doNotify(event);
 *         }
 *     }
 *
 *     {@literal @}Override
 *     public void subscribe(IEventSubscriber<KeyboardEvent> subscriber) {
 *         subscribers.add(subscriber);
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of event to be published
 */
public interface IPublisher<T> {

    /**
     * Called to publish an event or message to all subscribers.
     * Implementing classes should define the actions to be taken
     * to notify subscribers when an event occurs.
     */
    void publish();

    /**
     * Adds a subscriber that will be notified of events.
     *
     * @param subscriber the subscriber to add
     */
    void subscribe(IEventSubscriber<T> subscriber);
}
