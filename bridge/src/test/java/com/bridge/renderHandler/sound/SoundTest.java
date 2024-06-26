package com.bridge.renderHandler.sound;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class SoundTest {
    @Test
    void testSoundCreation() {
        Sound sound = new Sound(Paths.get("sound.wav"));

        assertFalse(sound.isPlaying());
        assertEquals(Paths.get("sound.wav"), sound.getPath());
    }

    @Test
    void testSetPlaying() {
        Sound sound = new Sound(Paths.get("sound.wav"));
        sound.setPlaying(true);
        assertTrue(sound.isPlaying());
        assertFalse(sound.canPlay());
    }
}
