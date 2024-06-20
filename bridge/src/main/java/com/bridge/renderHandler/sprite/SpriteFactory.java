package com.bridge.renderHandler.sprite;

import com.bridge.renderHandler.Coord;
import com.bridge.renderHandler.Size;
import com.bridge.renderHandler.factory.*;

/**
 * SpriteFactory class that implements ISpriteFactory.
 */
public class SpriteFactory implements ISpriteFactory {
    @Override
    public Sprite createSprite() {
        return new Sprite(new Coord(0, 0, 0), new Size(0, 0), "");
    }
}
