package com.bridge.renderHandler.repository;

import static org.junit.jupiter.api.Assertions.*;

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
                new Sprite(new Coord(10, 20), 0, new Size(50.5, 75.5), Paths.get("sprite1.png"));
        Sprite sprite2 =
                new Sprite(new Coord(30, 40), 1, new Size(60.5, 85.5), Paths.get("sprite2.png"));
        Sprite sprite3 =
                new Sprite(new Coord(30, 40), 2, new Size(60.5, 85.5), Paths.get("sprite3.png"));

        repository.add(sprite1);
        repository.add(sprite2);
        repository.add(sprite3);
        List<Sprite> sprites = repository.retrieve();

        assertEquals(3, sprites.size());
        assertEquals(sprite3, sprites.get(2));
        assertEquals(sprite2, sprites.get(1));

        sprite1.setZ_index(2);
        sprite2.setZ_index(1);
        sprite3.setZ_index(0);

        assertEquals(0, sprites.get(2).getZ_index());
        assertEquals(1, sprites.get(1).getZ_index());
        assertEquals(2, sprites.get(0).getZ_index());

        repository.updateZ();
        sprites = repository.retrieve();

        assertEquals(3, sprites.size());
        assertEquals(sprite1, sprites.get(2));
        assertEquals(sprite2, sprites.get(1));
    }
}
