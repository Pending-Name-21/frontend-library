package com.bridge.processinputhandler.listeners;

import java.util.List;

/**
 * Interface for input listeners.
 *
 * Implementors of this interface should provide a native implementation for the listen method
 */
public interface InputListener {

    /**
     * Listens for input events.
     *
     * @return the list of input events
     */
    List<String> listen();
}