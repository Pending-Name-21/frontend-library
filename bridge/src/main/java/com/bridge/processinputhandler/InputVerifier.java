package com.bridge.processinputhandler;

import java.util.List;

/**
 * The InputVerifier class is responsible for verifying input by fetching and
 * checking events from multiple publishers and notifying their respective subscribers.
 *
 * <p>This class collaborates with objects that implement the IPublisher interface
 * to ensure that input events are published and handled appropriately.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List<IPublisher> publishers = Arrays.asList(new KeyboardEventPublisher(), new MouseEventPublisher());
 * InputVerifier inputVerifier = new InputVerifier(publishers);
 * inputVerifier.check();
 * </pre>
 */
public class InputVerifier {
    private final List<IPublisher<?>> publishers;

    /**
     * Constructs an InputVerifier with the given list of publishers.
     *
     * @param publishers the list of IPublisher instances that will be used to fetch and check input events
     */
    public InputVerifier(List<IPublisher<?>> publishers) {
        this.publishers = publishers;
    }

    /**
     * Checks input events from all publishers and notifies their subscribers.
     * This method iterates through each publisher in the list and calls its publish method,
     * triggering the notification process for each event.
     */
    public void check() {
        publishers.forEach(IPublisher::publish);
    }
}
