package com.bridge.processinputhandler;

import com.bridge.processinputhandler.listeners.InputListener;

import java.util.List;

/**
 * Verifies input by fetching and checking events.
 */
public class InputVerifier {
    private ProcessInputPublisher processInputPublisher;
    private List<InputListener> inputListeners;

    /**
     * Constructs an InputVerifier with the specified publisher and listeners.
     *
     * @param processInputPublisher the publisher of input events
     * @param inputListeners the list of input listeners
     */
    public InputVerifier(
            ProcessInputPublisher processInputPublisher,
            List<InputListener> inputListeners
    ) {
        this.processInputPublisher = processInputPublisher;
        this.inputListeners = inputListeners;
    }

    /**
     * Checks input events and notifies subscribers.
     */
    public void check() {
        fetchRequiredEvents();
    }

    /**
     * Fetches required events from input listeners and notifies subscribers.
     */
    public void fetchRequiredEvents() {
        for (InputListener listener : inputListeners) {
            List<String> events = listener.listen();
            for (String event : events) {
                processInputPublisher.notifySubscribers(new EventType(event));
            }
        }
    }
}
