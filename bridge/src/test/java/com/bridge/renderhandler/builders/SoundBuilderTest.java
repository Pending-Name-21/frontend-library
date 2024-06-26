package com.bridge.renderhandler.builders;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.renderHandler.builders.SoundBuilder;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.sound.Sound;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SoundBuilderTest {
    private SoundRepository soundRepository;
    private Path existingFilePath;

    @BeforeEach
    void setUp() throws Exception {
        soundRepository = new SoundRepository();
        existingFilePath = Files.createTempFile("test_sound", ".wav");
    }

    @Test
    void testBuildSound() throws NonExistentFilePathException {
        SoundBuilder builder = new SoundBuilder(soundRepository);
        builder.buildPath(existingFilePath.toString());
        Sound sound = builder.assemble();

        assertNotNull(sound);
        assertEquals(existingFilePath, sound.getPath());
        assertFalse(sound.isPlaying());
    }

    @Test
    void testBuildSoundWithNonExistingFile() {
        SoundBuilder builder = new SoundBuilder(soundRepository);
        assertThrows(
                NonExistentFilePathException.class,
                () -> builder.buildPath("non_existing_sound.wav").assemble());
    }
}
