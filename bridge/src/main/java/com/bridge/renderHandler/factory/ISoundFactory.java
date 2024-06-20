package com.bridge.renderHandler.factory;

import com.bridge.renderHandler.sound.*;

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
