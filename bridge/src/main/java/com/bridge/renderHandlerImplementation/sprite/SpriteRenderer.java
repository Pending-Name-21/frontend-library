package com.bridge.renderHandlerImplementation.sprite;

import java.util.ArrayList;
import java.util.List;

public class SpriteRenderer {
    private List<Sprite> sprites = new ArrayList<>();
    private SpriteFactory spriteFactory;

    public SpriteRenderer(SpriteFactory spriteFactory) {
        this.spriteFactory = spriteFactory;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void updateSprite(Sprite sprite, Sprite newSprite) {
        int index = sprites.indexOf(sprite);
        if (index != -1) {
            sprites.set(index, newSprite);
        }
    }

    public void renderSprite(Sprite sprite) {
        
    }
}
