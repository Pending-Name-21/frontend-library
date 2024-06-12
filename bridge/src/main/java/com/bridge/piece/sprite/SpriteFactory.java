package com.bridge.piece.sprite;

import com.bridge.piece.Coord;
import com.bridge.piece.Size;
import com.bridge.piece.factory.*;

/**
 * SpriteFactory class that implements ISpriteFactory.
 */
public class SpriteFactory implements ISpriteFactory {
    @Override
    public Sprite createSprite() {
        return new Sprite(new Coord(0, 0), new Size(0, 0), "");
    }
}