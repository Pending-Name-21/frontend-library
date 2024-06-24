package com.bridge.processinputhandler;

import CoffeeTime.InputEvents.KeyboardEvent;

/**
 * The IKeyboardEventSubscriber interface provides a method for handling keyboard events.
 * Implementing classes should define how to process keyboard events when notified.
 *
 * <p>This interface is typically used in a publisher-subscriber pattern where
 * objects interested in keyboard events implement this interface to receive
 * notifications.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * public class MyKeyboardEventSubscriber implements IKeyboardEventSubscriber {
 *     {@literal @}Override
 *     public void doNotify(KeyboardEvent event) {
 *         // Handle the keyboard event
 *         System.out.println("Key pressed: " + event.getKey());
 *     }
 * }
 * </pre>
 */
public interface IKeyboardEventSubscriber {

    /**
     * Called to notify the subscriber of a keyboard event.
     * Implementing classes should define the actions to be taken
     * when a keyboard event occurs.
     *
     * @param event the keyboard event that occurred
     */
    void doNotify(KeyboardEvent event);
}
