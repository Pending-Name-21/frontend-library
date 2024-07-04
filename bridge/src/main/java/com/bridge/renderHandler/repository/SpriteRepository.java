package com.bridge.renderHandler.repository;

import com.bridge.renderHandler.sprite.Sprite;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Override
    public void delete(Sprite sprite) {
        sprites.remove(sprite);
    }

    /**
     * Retrieves only visible sprites from the repository.
     *
     * @return a list of visible sprites from the repository.
     */
    @Override
    public List<Sprite> retrieve() {
        sprites.sort(Comparator.comparingInt(s -> s.getZ_index()));
        return sprites.stream().filter(sprite -> !sprite.isHidden()).toList();
    }
}
