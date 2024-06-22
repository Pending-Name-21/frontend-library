package com.bridge.processinputhandler;

import com.bridge.core.exceptions.processinputhandler.FetchingEventsException;
import java.util.List;

/**
 * Verifies input by fetching and checking events.
 */
public class InputVerifier {
    private final List<IPublisher> publishers;

    public InputVerifier(List<IPublisher> publishers) {
        this.publishers = publishers;
    }

    /**
     * Checks input events and notifies subscribers.
     */
    public void check() {
        publishers.forEach(IPublisher::publish);
    }
}
