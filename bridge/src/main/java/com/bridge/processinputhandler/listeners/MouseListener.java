package com.bridge.processinputhandler.listeners;

import java.util.List;
import java.util.Arrays;

/**
 * Listens for mouse input events.
 */
public class MouseListener implements InputListener {

    /**
     * Listens for mouse input events.
     *
     * @return the list of mouse input events
     */
    @Override
    public List<String> listen() {
        return Arrays.asList("Mouse Clicked", "Mouse Moved");
    }
}