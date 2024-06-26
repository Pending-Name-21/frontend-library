package com.bridge.renderHandler.render;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.core.exceptions.renderHandlerExceptions.SoundException;

/**
 * RenderManager class that manages the rendering of sprites and playing of sounds.
 */
public class RenderManager {
    public SpriteRenderer spriteRenderer;
    public SoundPlayer soundPlayer;
//    public boolean spritesRendered = false;
//    public boolean soundsPlayed = false;

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
        try {
            spriteRenderer.renderAllSprites();
//            spritesRendered = true;
        } catch (RenderException e) {
            System.err.println("Error rendering sprites: " + e.getMessage());
            e.printStackTrace();
//            spritesRendered = false;
        }
    }

    /**
     * Plays all sounds.
     */
    public void playSounds() {
        try {
            soundPlayer.playAllSounds();
//            soundsPlayed = true;
        } catch (SoundException e) {
            System.err.println("Error playing sounds: " + e.getMessage());
            e.printStackTrace();
//            soundsPlayed = false;
        }
    }
}
