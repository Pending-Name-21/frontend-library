package com.bridge.renderHandler.render;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;

/**
 * RenderManager class that manages the rendering of sprites and playing of sounds.
 */
public class RenderManager {
    private final IRepository<Sprite> spriteIRepository;
    private final IRepository<Sound> soundIRepository;
    private final Transmitter transmitter;
    public static int FRAMES_LOST_THRESHOLD = 60;

    /**
     * Constructs a RenderManager with the specified repositories and transmitter.
     */
    public RenderManager() {
        transmitter = new Transmitter(new SocketClient(SocketClient.NAMESPACE));
        soundIRepository = new SoundRepository();
        spriteIRepository = new SpriteRepository();
    }

    /**
     * Renders the sprites and sounds.
     */
    public void render() throws RenderException {
        Frame frame = new Frame(spriteIRepository.retrieve(), soundIRepository.retrieve());
        transmitter.send(frame);
    }

    public IRepository<Sprite> getSpriteIRepository() {
        return spriteIRepository;
    }

    public IRepository<Sound> getSoundIRepository() {
        return soundIRepository;
    }
}
