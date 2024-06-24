package com.bridge.gamesettings;

/**
 * Interface for objects that want to be notified of game settings changes.
 */
public interface GameSettingsObserver {
    /**
     * Called when the game settings change.
     *
     * @param gameSettings the updated game settings
     */
    void onGameSettingsChanged(AGameSettings gameSettings);
}