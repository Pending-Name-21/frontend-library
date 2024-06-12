package com.bridge.renderHandler.sprite;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import java.util.ArrayList;
import java.util.List;

/**
 * SpriteRenderer class that manages a collection of sprites and their rendering.
 */
public class SpriteRenderer {
    private List<Sprite> sprites;
    private SpriteFactory spriteFactory;

    /**
     * Constructor that initializes the sprite list and sprite factory.
     *
     * @param spriteFactory the sprite factory
     */
    public SpriteRenderer(SpriteFactory spriteFactory) {
        this.sprites = new ArrayList<>();
        this.spriteFactory = spriteFactory;
    }

    public SpriteRenderer() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the list.
     *
     * @param sprite the sprite to add
     */
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    /**
     * Removes a sprite from the list.
     *
     * @param sprite the sprite to remove
     */
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    /**
     * Updates a sprite in the list.
     *
     * @param sprite the current sprite
     * @param newSprite the new sprite
     */
    public void updateSprite(Sprite sprite, Sprite newSprite) {
        int index = sprites.indexOf(sprite);
        if (index != -1) {
            sprites.set(index, newSprite);
        }
    }

    /**
     * Renders a specific sprite.
     *
     * @param sprite the sprite to render
     */
    public native void renderSprite(Sprite sprite) throws RenderException;

    /**
     * Renders all sprites in the list.
     */
    public void renderAllSprites() throws RenderException {
        for (Sprite sprite : sprites) {
            try {
                renderSprite(sprite);
            } catch (Exception e) {
                throw new RenderException("Failed to render sprite: " + sprite, e);
            }
        }
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}
