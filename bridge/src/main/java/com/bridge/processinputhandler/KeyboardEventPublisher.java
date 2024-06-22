package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.KeyboardEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyboardEventPublisher implements IPublisher{
    private final ConcurrentLinkedQueue<KeyboardEvent> buffer;
    private final List<IKeyboardEventSubscriber> subscribers;

    public KeyboardEventPublisher(ConcurrentLinkedQueue<KeyboardEvent> buffer) {
        this.buffer = buffer;
        subscribers = new ArrayList<>();
    }

    public void subscribe(IKeyboardEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void publish() {
        subscribers.forEach(sub -> {
            buffer.forEach(sub::doNotify);
        });
        buffer.clear();
    }
}
