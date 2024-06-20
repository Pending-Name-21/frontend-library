package com.bridge.renderHandler.sound;

import com.bridge.core.exceptions.renderHandlerExceptions.SoundException;
import com.bridge.renderHandler.sprite.Sprite;
import java.util.ArrayList;
import java.util.List;

/**
 * SoundPlayer class that manages a collection of sounds and their playback.
 */
public class SoundPlayer {
    private List<Sound> sounds;
    private SoundFactory soundFactory;

    /**
     * Constructor that initializes the sound list and sound factory.
     *
     * @param soundFactory the sound factory
     */
    public SoundPlayer(SoundFactory soundFactory) {
        this.sounds = new ArrayList<>();
        this.soundFactory = soundFactory;
    }

    public SoundPlayer() {
        this.sounds = new ArrayList<>();
    }

    /**
     * Adds a sound to the list.
     *
     * @param sound the sound to add
     */
    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    /**
     * Removes a sound from the list.
     *
     * @param sound the sound to remove
     */
    public void removeSound(Sound sound) {
        sounds.remove(sound);
    }

    /**
     * Removes the sound associated with a specific sprite.
     *
     * @param sprite the sprite whose sound should be removed
     */
    public void removeSoundFromSprite(Sprite sprite) {
        sounds.remove(sprite.getSound());
    }

    /**
     * Plays the sound associated with a specific sprite.
     *
     * @param sprite the sprite whose sound should be played
     */
    public native void playSound(Sprite sprite);

    /**
     * Plays a specific sound.
     *
     * @param sound the sound to play
     */
    public native void playSound(Sound sound);

    /**
     * Plays all sounds in the list.
     *
     * @throws SoundException if there is an error playing any sound
     */
    public void playAllSounds() throws SoundException {
        for (Sound sound : sounds) {
            try {
                playSound(sound);
            } catch (Exception e) {
                throw new SoundException("Failed to play sound: " + sound, e);
            }
        }
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}
