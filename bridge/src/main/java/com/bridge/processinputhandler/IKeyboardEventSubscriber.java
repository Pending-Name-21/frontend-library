package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.KeyboardEvent;

public interface IKeyboardEventSubscriber {
    void doNotify(KeyboardEvent event);
}
