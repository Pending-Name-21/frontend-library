package com.bridge.renderHandler.repository;
import com.bridge.renderHandler.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for storing and retrieving Sprite objects.
 */
public class SpriteRepository implements IRepository<Sprite> {
    private List<Sprite> sprites;

    public SpriteRepository() {
        sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the repository.
     *
     * @param sprite the item to add.
     */
    @Override
    public void add(Sprite sprite) {
        sprites.add(sprite);
    }

    /**
     * Retrieves all sprites from the repository.
     *
     * @return an array of sprites from the repository.
     */
    @Override
    public Sprite[] retrieve() {
        return sprites.toArray(new Sprite[0]);
    }
}
