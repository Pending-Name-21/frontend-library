package com.bridge.processinputhandler;

/**
 * The IEventSubscriber interface should be implemented by any class
 * that wishes to receive notifications about events of type <T>.
 *
 * @param <T> the type of event to be notified about
 */
public interface IEventSubscriber<T> {

    /**
     * Called to notify the subscriber about an event.
     *
     * @param event the event to notify the subscriber about
     */
    void doNotify(T event);
}
