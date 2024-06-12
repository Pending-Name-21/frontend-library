package com.bridge.renderHandler.sound;

import com.bridge.renderHandler.factory.ISoundFactory;

/**
 * SoundFactory class that implements ISoundFactory.
 */
public class SoundFactory implements ISoundFactory {
    @Override
    public Sound createSound() {
        return new Sound("") {
            @Override
            public boolean canPlay() {
                return true;
            }
        };
    }
}
