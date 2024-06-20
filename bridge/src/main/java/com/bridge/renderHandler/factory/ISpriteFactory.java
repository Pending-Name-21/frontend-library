package com.bridge.renderHandler.factory;

import com.bridge.renderHandler.sprite.*;

/**
 * ISpriteFactory interface for sprite creation.
 */
public interface ISpriteFactory {

    /**
     * Creates a new sprite.
     *
     * @return the sprite created
     */
    Sprite createSprite();
}
