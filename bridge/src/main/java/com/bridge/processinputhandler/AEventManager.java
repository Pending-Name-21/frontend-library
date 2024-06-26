package com.bridge.processinputhandler;

import com.bridge.ipc.IEventBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AEventManager<T> implements IPublisher<T>, IEventBuffer {
    protected final ConcurrentLinkedQueue<T> events;
    protected final List<IEventSubscriber<T>> subscribers;

    public AEventManager() {
        this.events = new ConcurrentLinkedQueue<>();
        subscribers = new ArrayList<>();
    }
}
