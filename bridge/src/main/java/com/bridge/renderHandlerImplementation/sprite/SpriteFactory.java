package com.bridge.renderHandlerImplementation.sprite;

import com.bridge.renderHandlerImplementation.factory.*;;

public class SpriteFactory implements ISpriteFactory {
    @Override
    public Sprite createSprite() {

        return new Sprite();
    }
}
