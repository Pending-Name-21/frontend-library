package com.bridge.piece.factory;

import com.bridge.piece.sound.*;

/**
 * ISoundFactory interface for sound creation.
 */
public interface ISoundFactory {

     /**
     * Cretes a new sound.
     * 
     * @return the sound created
     */
    Sound createSound();
}
