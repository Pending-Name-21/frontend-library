package com.bridge.renderHandler.render;

import com.bridge.renderHandler.sound.Sound;

public class SoundTest extends Sound {
    /**
     * Constructor that initializes a sound.
     *
     * @param path the file path of the sound
     */
    public SoundTest(String path) {
        super(path);
    }

    @Override
    public boolean canPlay() {
        return false;
    }
}
