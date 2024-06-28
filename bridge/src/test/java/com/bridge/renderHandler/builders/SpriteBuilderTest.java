package com.bridge.renderHandler.builders;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sprite.Sprite;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpriteBuilderTest {
    private SpriteRepository spriteRepository;
    private Path existingFilePath;

    @BeforeEach
    void setUp() throws Exception {
        spriteRepository = new SpriteRepository();
        existingFilePath = Files.createTempFile("test_sprite", ".jpg");
    }

    @Test
    void testBuildSprite() throws NonExistentFilePathException, IOException {
        SpriteBuilder builder = new SpriteBuilder(spriteRepository);
        builder.buildCoord(10, 20).buildSize(50.5, 75.5).buildPath(existingFilePath.toString());
        Sprite sprite = builder.assemble();

        assertNotNull(sprite);
        assertEquals(10, sprite.getPosition().getX());
        assertEquals(20, sprite.getPosition().getY());
        assertEquals(50.5, sprite.getSize().getHeight());
        assertEquals(75.5, sprite.getSize().getWidth());
        assertEquals(existingFilePath, sprite.getPath());
    }

    @Test
    void testBuildSpriteWithNonExistingFile() {
        SpriteBuilder builder = new SpriteBuilder(spriteRepository);
        assertThrows(
                NonExistentFilePathException.class,
                () -> builder.buildPath("non_existing_sprite.jpg").assemble());
    }
}
