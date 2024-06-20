package com.bridge.updatehandler;

import com.bridge.core.exceptions.updatehandler.NotPossibleToNotifySubscribersException;
import java.util.ArrayList;
import java.util.List;

/**
 * Publishes updates to subscribers.
 */
public class UpdatePublisher {
    private List<IUpdateSubscriber> subscribers;

    /**
     * Constructs an UpdatePublisher and initializes the list of subscribers.
     */
    public UpdatePublisher() {
        this.subscribers = new ArrayList<>();
    }

    /**
     * Subscribes a subscriber to updates.
     *
     * @param subscriber the subscriber to notify
     */
    public void subscribe(IUpdateSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    /**
     * Notifies all subscribers of an update.
     */
    public void notifySubscribers() throws NotPossibleToNotifySubscribersException {
        try {
            for (IUpdateSubscriber subscriber : subscribers) {
                subscriber.notifySubscriber();
            }
        } catch (Exception e) {
            throw new NotPossibleToNotifySubscribersException(e.getCause());
        }
    }
}
