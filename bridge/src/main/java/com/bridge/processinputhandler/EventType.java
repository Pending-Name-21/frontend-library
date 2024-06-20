package com.bridge.processinputhandler;

/**
 * Represents an event type with a name.
 */
public class EventType {
    private String name;

    /**
     * Constructs an EventType with the specified name.
     *
     * @param name the name of the event
     */
    public EventType(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the event.
     *
     * @return the name of the event
     */
    public String getName() {
        return name;
    }
}
