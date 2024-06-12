package com.bridge.renderHandler.sound;

import com.bridge.renderHandler.*;

/**
 * Abstract Sound class that represents a sound.
 */
public abstract class Sound implements IFile {

    private String path;

    /**
     * Constructor that initializes a sound.
     *
     * @param path the file path of the sound
     */
    public Sound(String path) {
        this.path = path;
    }

    /**
     * Abstract method to check if the sound can be played.
     *
     * @return true if the sound can be played, false otherwise
     */
    public abstract boolean canPlay();

    @Override
    public String getPath() {
        return this.path;
    }
}
