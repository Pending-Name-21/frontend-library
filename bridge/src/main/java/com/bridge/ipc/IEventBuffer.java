package com.bridge.ipc;

import CoffeeTime.InputEvents.Event;

public interface IEventBuffer {
    void feed(Event event);
}
