package com.bridge.processinputhandler.listeners;

import java.util.List;

/**
 * Listens for keyboard input events.
 */
public class KeyboardListener implements InputListener {

    /**
     * Native method to listen for keyboard input events.
     *
     * @return the list of keyboard input events
     */
    @Override
    public native List<String> listen();
}
