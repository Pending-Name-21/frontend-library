package com.bridge.renderHandler.render;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;

/**
 * RenderManager class that manages the rendering of sprites and playing of sounds.
 */
public class RenderManager {
    private final IRepository<Sprite> spriteRepository;
    private final IRepository<Sound> soundRepository;
    private final Transmitter transmitter;
    public static int FRAMES_LOST_THRESHOLD = 60;

    /**
     * Constructs a RenderManager with the specified repositories and transmitter.
     *
     * @param spriteRepository the repository for storing sprites.
     * @param soundRepository  the repository for storing sounds.
     */
    public RenderManager(IRepository<Sprite> spriteRepository, IRepository<Sound> soundRepository) {
        transmitter = new Transmitter(new SocketClient(SocketClient.NAMESPACE));
        this.spriteRepository = spriteRepository;
        this.soundRepository = soundRepository;
    }

    /**
     * Renders the sprites and sounds.
     */
    public void render() throws RenderException {
        Frame frame = new Frame(spriteRepository.retrieve(), soundRepository.retrieve());
        transmitter.send(frame);
    }
}
