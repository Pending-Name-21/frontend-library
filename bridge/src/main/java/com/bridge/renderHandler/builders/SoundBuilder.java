package com.bridge.renderHandler.builders;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.renderHandler.file.FileHandler;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sound.Sound;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * Sets the file path for the Sound file from the screen files
     * without validating non-existing files
     *
     * @param filePath the file path.
     * @return the current instance of SoundBuilder.
     */
    public SoundBuilder buildPathFromScreenFiles(String filePath) {
        this.filePath = Paths.get(filePath);
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
