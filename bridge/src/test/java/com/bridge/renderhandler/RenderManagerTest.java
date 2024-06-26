package com.bridge.renderhandler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bridge.renderHandler.render.RenderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RenderManagerTest {
    private SpriteRenderer spriteRenderer;
    private SoundPlayer soundPlayer;
    private RenderManager renderManager;
    private SpriteFactory spriteFactory;
    private SoundFactory soundFactory;

    @BeforeEach
    void setUp() {
        spriteFactory = new SpriteFactory();
        soundFactory = new SoundFactory();
        spriteRenderer = new SpriteRenderer(spriteFactory);
        soundPlayer = new SoundPlayer(soundFactory);
        renderManager = new RenderManager(spriteRenderer, soundPlayer);
    }

    @Test
    void testRenderSprites() {
        renderManager.renderSprites();
        assertTrue(renderManager.spritesRendered, "Sprites should be rendered");
    }

    @Test
    void testPlaySounds() {
        renderManager.playSounds();
        assertTrue(renderManager.soundsPlayed, "Sounds should be played");
    }

    @Test
    void testDefaultConstructor() {
        renderManager = new RenderManager();
        assertTrue(renderManager.spriteRenderer != null, "SpriteRenderer should be initialized");
        assertTrue(renderManager.soundPlayer != null, "SoundPlayer should be initialized");
    }
}
