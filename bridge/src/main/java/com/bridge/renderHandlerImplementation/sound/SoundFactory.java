package com.bridge.renderHandlerImplementation.sound;

import com.bridge.renderHandlerImplementation.factory.*;

public class SoundFactory implements ISoundFactory {
    @Override
    public Sound createSound() {
        return new Sound();
    }
}
