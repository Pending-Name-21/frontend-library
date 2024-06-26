package com.bridge.renderhandler.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Size;
import com.bridge.renderHandler.sprite.Sprite;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class SpriteRepositoryTest {
    @Test
    void testAddAndRetrieveSprites() {
        SpriteRepository repository = new SpriteRepository();
        Sprite sprite1 =
                new Sprite(new Coord(10, 20), new Size(50.5, 75.5), Paths.get("sprite1.png"));
        Sprite sprite2 =
                new Sprite(new Coord(30, 40), new Size(60.5, 85.5), Paths.get("sprite2.png"));

        repository.add(sprite1);
        repository.add(sprite2);

        List<Sprite> sprites = repository.retrieve();
        assertEquals(2, sprites.size());
        assertEquals(sprite1, sprites.get(0));
        assertEquals(sprite2, sprites.get(1));
    }
}
