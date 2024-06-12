package com.bridge.renderHandler.render;

import com.bridge.renderHandler.sound.SoundFactory;
import com.bridge.renderHandler.sound.SoundPlayer;
import com.bridge.renderHandler.sprite.SpriteFactory;
import com.bridge.renderHandler.sprite.SpriteRenderer;

/**
 * RenderManager class that manages the rendering of sprites and playing of sounds.
 */
public class RenderManager {
    public SpriteRenderer spriteRenderer;
    public SoundPlayer soundPlayer;
    
    public boolean spritesRendered = false;
    public boolean soundsPlayed = false;

    /**
     * Constructor that initializes the rendering and sound playing components.
     */
    public RenderManager() {
        SpriteFactory spriteFactory = new SpriteFactory();
        SoundFactory soundFactory = new SoundFactory();
        this.spriteRenderer = new SpriteRenderer(spriteFactory);
        this.soundPlayer = new SoundPlayer(soundFactory);
    }

    /**
     * Constructor for testing with mocked dependencies.
     */
    public RenderManager(SpriteRenderer spriteRenderer, SoundPlayer soundPlayer) {
        this.spriteRenderer = spriteRenderer;
        this.soundPlayer = soundPlayer;
    }

    /**
     * Renders all sprites.
     */
    public void renderSprites() {
        spriteRenderer.renderAllSprites();
        spritesRendered = true;
    }

    /**
     * Plays all sounds.
     */
    public void playSounds() {
        soundPlayer.playAllSounds();
        soundsPlayed = true;
    }
}
