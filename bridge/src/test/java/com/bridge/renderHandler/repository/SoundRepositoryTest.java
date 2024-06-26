package com.bridge.renderHandler.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.renderHandler.sound.Sound;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoundRepositoryTest {
    @Test
    void testAddAndRetrieveSounds() {
        SoundRepository repository = new SoundRepository();
        Sound sound1 = new Sound(Paths.get("sound1.wav"));
        Sound sound2 = new Sound(Paths.get("sound2.wav"));
        sound2.setPlaying(true);

        repository.add(sound1);
        repository.add(sound2);

        List<Sound> sounds = repository.retrieve();
        assertEquals(1, sounds.size());
        assertEquals(sound1, sounds.get(0));
    }
}
