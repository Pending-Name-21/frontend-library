package com.bridge.processinputhandler.listeners;

import java.util.List;

/**
 * Interface for input listeners.
 */
public interface InputListener {

    /**
     * Listens for input events.
     *
     * @return the list of input events
     */
    List<String> listen();
}