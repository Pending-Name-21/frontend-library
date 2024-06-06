package com.bridge.processinputhandler.listeners;

import java.util.List;
import java.util.Arrays;

/**
 * Listens for keyboard input events.
 */
public class KeyboardListener implements InputListener {

    /**
     * Listens for keyboard input events.
     *
     * @return the list of keyboard input events
     */
    @Override
    public List<String> listen() {
        return Arrays.asList("Key Pressed", "Key Released");
    }
}