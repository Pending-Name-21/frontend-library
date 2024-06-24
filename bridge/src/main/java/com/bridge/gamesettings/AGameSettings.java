package com.bridge.gamesettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for game settings.
 */
public abstract class AGameSettings {

    private final List<GameSettingsObserver> observers;

    public AGameSettings() {
        observers = new ArrayList<>();
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, and false if it keeps running
     */
    public abstract boolean isGameOver();

    /**
     * Adds an observer to be notified of game settings changes.
     *
     * @param observer the observer to add
     */
    public void addObserver(GameSettingsObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer so it no longer receives notifications.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(GameSettingsObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a change in the game settings.
     */
    protected void notifyObservers() {
        for (GameSettingsObserver observer : observers) {
            observer.onGameSettingsChanged(this);
        }
    }
}
