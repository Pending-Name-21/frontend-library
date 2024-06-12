package com.bridge.piece;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RenderManagerTest {

    @Test
    public void testRenderSprites() {
        RenderManager renderManager = new RenderManager();
        Sprite sprite1 = new Sprite(new Coord(0, 0), new Size(10, 10), "sprite1.png");
        Sprite sprite2 = new Sprite(new Coord(20, 20), new Size(15, 15), "sprite2.png");
        renderManager.getSpriteRenderer().addSprite(sprite1);
        renderManager.getSpriteRenderer().addSprite(sprite2);

       
        assertDoesNotThrow(() -> renderManager.renderSprites());
    }

    @Test
    public void testPlaySounds() {
        RenderManager renderManager = new RenderManager();
        Sound sound1 = new Sound("sound1.mp3") {
            @Override
            public boolean canPlay() {
                return true;
            }
        };
        Sound sound2 = new Sound("sound2.mp3") {
            @Override
            public boolean canPlay() {
                return true;
            }
        };
        renderManager.getSoundPlayer().addSound(sound1);
        renderManager.getSoundPlayer().addSound(sound2);

        
        assertDoesNotThrow(() -> renderManager.playSounds());
    }
}
