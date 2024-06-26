package com.bridge.processinputhandler;

public interface IEventSubscriber<T> {
    void doNotify(T event);
}
