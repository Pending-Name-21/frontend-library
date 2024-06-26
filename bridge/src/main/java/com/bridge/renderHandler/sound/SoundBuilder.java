package com.bridge.renderHandler.sound;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.renderHandler.file.FileHandler;
import com.bridge.renderHandler.repository.IRepository;

import java.nio.file.Path;

/**
 * Builder class for creating Sound objects.
 */
public class SoundBuilder {
    private IRepository<Sound> soundRepository;
    private final FileHandler fileHandler;
    private Path filePath;

    /**
     * Constructs a SoundBuilder with the specified repository and file handler.
     *
     * @param soundRepository the repository for storing sounds.
     */
    public SoundBuilder(IRepository<Sound> soundRepository) {
        this.soundRepository = soundRepository;
        this.fileHandler = new FileHandler();
    }

    /**
     * Sets the file path for the Sound file.
     *
     * @param filePath the file path.
     * @return the current instance of SoundBuilder.
     */
    public SoundBuilder buildPath(String filePath) throws NonExistentFilePathException {
        this.filePath = fileHandler.getFilePath(filePath);
        return this;
    }

    /**
     * Assembles the Sound object from the set properties.
     *
     * @return the assembled Sound.
     */
    public Sound assemble() {
        Sound sound = new Sound(filePath);
        soundRepository.add(sound);
        return sound;
    }
}

