package com.bridge.processinputhandler;

import com.bridge.core.exceptions.processinputhandler.FetchingEventsException;
import com.bridge.core.exceptions.processinputhandler.NullInputListenersException;
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
            ProcessInputPublisher processInputPublisher, List<InputListener> inputListeners)
            throws NullInputListenersException {
        this.processInputPublisher = processInputPublisher;
        if (inputListeners == null) throw new NullInputListenersException();
        this.inputListeners = inputListeners;
    }

    /**
     * Checks input events and notifies subscribers.
     */
    public void check() throws FetchingEventsException {
        fetchRequiredEvents();
    }

    /**
     * Fetches required events from input listeners and notifies subscribers.
     */
    public void fetchRequiredEvents() throws FetchingEventsException {
        try {
            for (InputListener listener : inputListeners) {
                List<String> events = listener.listen();
                for (String event : events) {
                    processInputPublisher.notifySubscribers(new EventType(event));
                }
            }
        } catch (Exception e) {
            throw new FetchingEventsException(e);
        }
    }
}
