package com.bridge.processinputhandler.listeners;

import java.util.List;
import java.util.Arrays;

/**
 * Listens for mouse input events.
 */
public class MouseListener implements InputListener {

    /**
     * Native method to listen for mouse input events.
     *
     * @return the list of mouse input events
     */
    @Override
    public native List<String> listen();
}