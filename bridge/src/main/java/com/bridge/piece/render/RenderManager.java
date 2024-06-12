package com.bridge.piece.render;

import com.bridge.piece.sound.SoundFactory;
import com.bridge.piece.sound.SoundPlayer;
import com.bridge.piece.sprite.SpriteFactory;
import com.bridge.piece.sprite.SpriteRenderer;

/**
 * RenderManager class that manages the rendering of sprites and playing of sounds.
 */
public class RenderManager {
    private SpriteRenderer spriteRenderer;
    private SoundPlayer soundPlayer;

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
     * Renders all sprites.
     */
    public void renderSprites() {
        spriteRenderer.renderAllSprites();
    }

    /**
     * Plays all sounds.
     */
    public void playSounds() {
        soundPlayer.playAllSounds();
    }
}
