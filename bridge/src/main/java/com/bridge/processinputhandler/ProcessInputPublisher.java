package com.bridge.processinputhandler;

import java.util.*;

/**
 * Publishes input events to subscribers.
 */
public class ProcessInputPublisher {
    private Map<EventType, List<IProcessInputSubscriber>> subscribers;

    /**
     * Constructs a ProcessInputPublisher and initializes the map of subscribers.
     */
    public ProcessInputPublisher() {
        this.subscribers = new HashMap<>();
    }

    /**
     * Subscribes a subscriber to an event.
     *
     * @param event the event to subscribe to
     * @param subscriber the subscriber to notify
     */
    public void subscribe(EventType event, IProcessInputSubscriber subscriber) {
        subscribers.computeIfAbsent(event, k -> new ArrayList<>()).add(subscriber);
    }

    /**
     * Gets the list of events.
     *
     * @return the list of events
     */
    List<EventType> getEvents() {
        return new ArrayList<>(subscribers.keySet());
    }

    /**
     * Notifies subscribers of an event.
     *
     * @param event the event to notify subscribers of
     */
    public void notifySubscribers(EventType event) {
        List<IProcessInputSubscriber> eventSubscribers = subscribers.get(event);
        if (eventSubscribers != null) {
            for (IProcessInputSubscriber subscriber : eventSubscribers) {
                subscriber.notify(event);
            }
        }
    }
}
