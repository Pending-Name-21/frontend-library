package inputsuscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Handler {
    protected Map<EventType, List<ISubscriber>> subscribers;

    public Handler() {
        this.subscribers = new HashMap<>();
    }

    public void suscribe(EventType event, ISubscriber subscriber) {
        if (!subscribers.containsKey(event)) {
            subscribers.put(event, new ArrayList<>());
        }
        subscribers.get(event).add(subscriber);
    }

    public void notifySubscribers(EventType eventType) {
        if (subscribers.containsKey(eventType)) {
            for (ISubscriber subscriber : subscribers.get(eventType)) {
                subscriber.notify(eventType);
            }
        } else {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, "No subscribers found for event type: " + eventType);
        }
    }
}
