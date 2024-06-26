package com.bridge.processinputhandler;

import com.bridge.ipc.IEventBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The abstract class AEventManager provides a base implementation for an event manager
 * that handles the publication and buffering of events. This class is designed to be
 * extended by concrete event manager implementations.
 *
 * @param <T> the type of events managed by this event manager
 */
public abstract class AEventManager<T> implements IPublisher<T>, IEventBuffer {

    /**
     * A thread-safe queue that holds the events to be processed.
     */
    protected final ConcurrentLinkedQueue<T> events;

    /**
     * A list of subscribers that are interested in receiving events.
     */
    protected final List<IEventSubscriber<T>> subscribers;

    /**
     * Constructs an AEventManager instance.
     * Initializes the events queue and the list of subscribers.
     */
    public AEventManager() {
        this.events = new ConcurrentLinkedQueue<>();
        this.subscribers = new ArrayList<>();
    }
}
