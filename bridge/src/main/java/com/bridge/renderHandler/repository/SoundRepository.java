package com.bridge.renderHandler.repository;

import com.bridge.renderHandler.sound.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for storing and retrieving Sound objects.
 */
public class SoundRepository implements IRepository<Sound> {
    private List<Sound> sounds;

    public SoundRepository() {
        sounds = new ArrayList<>();
    }

    /**
     * Adds a sound to the repository.
     *
     * @param sound the item to add.
     */
    @Override
    public void add(Sound sound) {
        sounds.add(sound);
    }

    /**
     * Retrieves only playable sounds from the repository.
     *
     * @return an array of playable sounds from the repository.
     */
    @Override
    public Sound[] retrieve() {
        return sounds.stream()
                .filter(Sound::canPlay)
                .toArray(Sound[]::new);
    }
}

