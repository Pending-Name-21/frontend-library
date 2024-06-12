package com.bridge.piece.sound;

import com.bridge.piece.factory.ISoundFactory;

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
