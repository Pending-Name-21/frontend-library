package com.bridge.extras;

import com.bridge.renderHandler.builders.SoundBuilder;
import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import com.bridge.updatehandler.IUpdateSubscriber;

/**
 * SplashScreen class that initializes and starts an animation
 * with a sprite and sound, and updates its state based on frame count.
 */
public class SplashScreen implements IUpdateSubscriber {
    private Sprite sprite;
    private Sound sound;
    private final IRepository<Sound> soundRepository;
    private final IRepository<Sprite> spriteRepository;
    private double framesCount;

    /**
     * Constructs a SplashScreen with the specified frame count.
     * Initializes sprite and sound using their respective repositories and builders.
     *
     */
    public SplashScreen(
            IRepository<Sprite> spriteRepository,
            IRepository<Sound> soundRepository,
            double framesCount) {
        this.framesCount = framesCount;
        this.spriteRepository = spriteRepository;
        this.soundRepository = soundRepository;
    }

    /**
     * Starts the animation by sending a frame containing the sprite and sound data
     * through a transmitter.
     */
    public void startAnimation() {
        SpriteBuilder spriteBuilder = new SpriteBuilder(spriteRepository);
        this.sprite =
                spriteBuilder
                        .buildPathFromScreenFiles("assets/images/splash_image.jpg")
                        .buildCoord(0, 0)
                        .buildSize(800, 600)
                        .assemble();
        SoundBuilder soundBuilder = new SoundBuilder(soundRepository);
        this.sound =
                soundBuilder.buildPathFromScreenFiles("assets/sounds/splash_sound.mp3").assemble();
    }

    /**
     * Updates the state of the splash screen based on the frame count.
     * Hides the sprite and stops the sound if the frame count exceeds 300.
     */
    @Override
    public void notifySubscriber() {
        if (framesCount >= 300 && !sprite.isHidden()) {
            sprite.setHidden(true);
            sound.setPlaying(false);
            sound.setCanPlay(false);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Sound getSound() {
        return sound;
    }

    public void setFramesCount(double framesCount) {
        this.framesCount = framesCount;
    }
}
