package com.bridge.renderHandler.render;

import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;


/**
 * RenderManager class that manages the rendering of sprites and playing of sounds.
 */
public class RenderManager {
    private IRepository<Sprite> spriteRepository;
    private IRepository<Sound> soundRepository;
//    private Transmitter transmitter;

    /**
     * Constructs a RenderManager with the specified repositories and transmitter.
     *
     * @param transmitter     the transmitter for managing rendering and sound playing.
     * @param spriteRepository the repository for storing sprites.
     * @param soundRepository  the repository for storing sounds.
     */
    /* public RenderManager(
        Transmitter transmitter,
        IRepository<Sprite> spriteRepository,
        IRepository<Sound> soundRepository
    ) {
        this.transmitter = transmitter;
        this.spriteRepository = spriteRepository;
        this.soundRepository = soundRepository;
    } */

    /**
     * Renders the sprites and sounds.
     */
     /* public void render() {
        Frame frame = new Frame(spriteRepository.retrieve(), soundRepository.retrieve());
        transmitter.send(frame);
    } */
}

