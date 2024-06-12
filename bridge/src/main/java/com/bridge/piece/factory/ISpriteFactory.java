package com.bridge.piece.factory;

import com.bridge.piece.sprite.*;

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
