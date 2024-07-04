package com.bridge.renderHandler.sound;

import com.bridge.renderHandler.file.IFile;
import java.nio.file.Path;

/**
 * Represents a sound with playback state and file path.
 */
public class Sound implements IFile {
    private boolean isPlaying;
    private final Path path;
    private boolean canPlay;

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    /**
     * Constructs a Sound with specified path.
     *
     * @param path the file path of the sound.
     */
    public Sound(Path path) {
        this.path = path;
        this.isPlaying = false;
        this.canPlay = true;
    }

    /**
     * Checks if the sound is being played.
     *
     * @return true if the sound is playing, false otherwise
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Sets the playing state of the sound.
     *
     * @param playing true to set the sound as playing, false otherwise
     */
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    /**
     * Determines if the sound can be played.
     *
     * @return true if the sound is not currently playing, false otherwise.
     */
    public boolean canPlay() {
        return canPlay;
    }

    /**
     * Gets the sound file path.
     *
     * @return the file path
     */
    @Override
    public Path getPath() {
        return path;
    }
}
