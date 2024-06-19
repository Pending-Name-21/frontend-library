package com.bridge.initializerhandler;

import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the initialization of game components.
 */
public class GameInitializer {
    private List<IIinitializerSubscriber> initializerSubscribers;

    /**
     * Constructs a GameInitializer and initializes the list of subscribers.
     */
    public GameInitializer() {
        this.initializerSubscribers = new ArrayList<>();
    }

    /**
     * Subscribes a subscriber for initialization.
     *
     * @param subscriber the subscriber to be initialized
     */
    public void subscribe(IIinitializerSubscriber subscriber) {
        this.initializerSubscribers.add(subscriber);
    }

    /**
     * Initializes all subscribed components.
     *
     * @throws NotPossibleToInitializeSubscribersException if initialization fails
     */
    public void initializeSubscribers() throws NotPossibleToInitializeSubscribersException {
        try {
            for (IIinitializerSubscriber subscriber : initializerSubscribers) {
                subscriber.init();
            }
        } catch (Exception e) {
            throw new NotPossibleToInitializeSubscribersException(e.getCause());
        }
    }
}
