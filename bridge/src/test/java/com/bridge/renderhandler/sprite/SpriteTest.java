package com.bridge.renderhandler.sprite;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Size;
import com.bridge.renderHandler.sprite.Sprite;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class SpriteTest {
    @Test
    void testSpriteCreation() {
        Coord position = new Coord(10, 20);
        Size size = new Size(50.5, 75.5);
        Sprite sprite = new Sprite(position, size, Paths.get("sprite.jpg"));

        assertEquals(position, sprite.getPosition());
        assertEquals(size, sprite.getSize());
        assertFalse(sprite.isHidden());
        assertEquals(Paths.get("sprite.jpg"), sprite.getPath());
    }

    @Test
    void testSetHidden() {
        Sprite sprite =
                new Sprite(new Coord(10, 20), new Size(50.5, 75.5), Paths.get("sprite.png"));
        sprite.setHidden(true);
        assertTrue(sprite.isHidden());
    }
}
