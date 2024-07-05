package com.bridge.renderHandler.render;

import com.bridge.core.exceptions.ipc.SocketClientException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.core.handlers.LogHandler;
import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import java.util.logging.Level;

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

        boolean success;
        do {
            success = attemptSend(frame);
        } while (!success);
    }

    private boolean attemptSend(Frame frame) throws RenderException {
        boolean success = false;
        try {
            transmitter.send(frame);
            success = true;
        } catch (SocketClientException e) {
            LogHandler.log(Level.WARNING, "Channel disconnecting scouting for server", e);
            SocketClient.scout();
        }

        return success;
    }

    public IRepository<Sprite> getSpriteIRepository() {
        return spriteIRepository;
    }

    public IRepository<Sound> getSoundIRepository() {
        return soundIRepository;
    }

    public void init() throws RenderException {
        SocketClient.scout();
    }
}
