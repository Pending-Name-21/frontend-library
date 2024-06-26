package com.bridge.renderHandler.sprite;

import static org.junit.jupiter.api.Assertions.*;

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
