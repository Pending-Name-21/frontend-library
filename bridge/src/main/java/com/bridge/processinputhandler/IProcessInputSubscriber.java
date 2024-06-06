package com.bridge.processinputhandler;

/**
 * Interface for process input subscribers.
 */
public interface IProcessInputSubscriber {

    /**
     * Notify the subscriber of an event.
     *
     * @param event the event that occurred
     */
    void notify(EventType event);
}
