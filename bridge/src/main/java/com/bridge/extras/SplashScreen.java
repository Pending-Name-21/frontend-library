package com.bridge.extras;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.initializerhandler.IIinitializerSubscriber;
import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.renderHandler.builders.SoundBuilder;
import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.render.Frame;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import com.bridge.updatehandler.IUpdateSubscriber;

/**
 * SplashScreen class that initializes and starts an animation
 * with a sprite and sound, and updates its state based on frame count.
 */
public class SplashScreen implements IIinitializerSubscriber, IUpdateSubscriber {
    private Sprite sprite;
    private Sound sound;
    private SoundRepository soundRepository;
    private SpriteRepository spriteRepository;
    private int framesCount;

    /**
     * Constructs a SplashScreen with the specified frame count.
     * Initializes sprite and sound using their respective repositories and builders.
     *
     * @param framesCount the number of frames for the splash screen animation
     * @throws NonExistentFilePathException if the file path for sprite or sound is invalid
     */
    public SplashScreen(int framesCount) throws NonExistentFilePathException {
        this.framesCount = framesCount;

        spriteRepository = new SpriteRepository();
        SpriteBuilder spriteBuilder = new SpriteBuilder(spriteRepository);
        this.sprite = spriteBuilder.buildPath("").assemble();

        soundRepository = new SoundRepository();
        SoundBuilder soundBuilder = new SoundBuilder(soundRepository);
        this.sound = soundBuilder.buildPath("").assemble();
    }

    /**
     * Starts the animation by sending a frame containing the sprite and sound data
     * through a transmitter.
     *
     * @throws RenderException if there is an error during the rendering process
     */
    public void startAnimation() throws RenderException {
        Transmitter transmitter = new Transmitter(new SocketClient(SocketClient.NAMESPACE));
        Frame frame = new Frame(spriteRepository.retrieve(), soundRepository.retrieve());
        transmitter.send(frame);
    }

    /**
     * Initializes the splash screen animation by calling startAnimation.
     */
    @Override
    public void init() {
        try {
            startAnimation();
        } catch (RenderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the state of the splash screen based on the frame count.
     * Hides the sprite and stops the sound if the frame count exceeds 300.
     */
    @Override
    public void notifySubscriber() {
        if (framesCount >= 300 && !sprite.isHidden() && !sound.isPlaying()) {
            sprite.setHidden(true);
            sound.setPlaying(false);
        }
    }
}
