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
                new Sprite(new Coord(10, 20,0), new Size(50.5, 75.5), Paths.get("sprite1.png"));
        Sprite sprite2 =
                new Sprite(new Coord(30, 40,1), new Size(60.5, 85.5), Paths.get("sprite2.png"));
        Sprite sprite3 =
                new Sprite(new Coord(30, 40,2), new Size(60.5, 85.5), Paths.get("sprite3.png"));

        repository.add(sprite1);
        repository.add(sprite2);
        repository.add(sprite3);

        sprite1.getPosition().setZ(2);
        sprite2.getPosition().setZ(1);
        sprite3.getPosition().setZ(0);

        repository.updateZ();

        List<Sprite> sprites = repository.retrieve();
        assertEquals(3, sprites.size());
        assertEquals(sprite1, sprites.get(2));
        assertEquals(sprite2, sprites.get(1));
    }
}
