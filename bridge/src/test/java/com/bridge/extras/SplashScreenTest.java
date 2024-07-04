package com.bridge.extras;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SplashScreenTest {
    private SplashScreen splashScreen;
    private SpriteRepository spriteRepository;
    private SoundRepository soundRepository;

    @BeforeEach
    void setUp() throws NonExistentFilePathException {
        spriteRepository = new SpriteRepository();
        soundRepository = new SoundRepository();
        splashScreen = new SplashScreen(spriteRepository, soundRepository);
    }

    @Test
    void testConstructor() {
        assertNotNull(splashScreen);
        assertNotNull(splashScreen.getSprite());
        assertNotNull(splashScreen.getSound());
    }

    @Test
    void testStartAnimation() {
        try {
            splashScreen.startAnimation();
        } catch (RenderException e) {
            fail("RenderException was thrown during startAnimation: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNotifySubscriber() throws NonExistentFilePathException {
        //        assertFalse(splashScreen.getSprite().isHidden());
        //        assertFalse(splashScreen.getSound().isPlaying());
        //
        //        splashScreen = new SplashScreen(301);
        //        splashScreen.notifySubscriber();
        //
        //        assertTrue(splashScreen.getSprite().isHidden());
        //        assertFalse(splashScreen.getSound().isPlaying());
    }

    @Test
    void testNotifySubscriberBeforeThreshold() throws NonExistentFilePathException {
        Sprite sprite = splashScreen.getSprite();
        Sound sound = splashScreen.getSound();

        assertFalse(sprite.isHidden());
        assertFalse(sound.isPlaying());

        splashScreen.setFramesCount(299);
        splashScreen.notifySubscriber();

        assertFalse(sprite.isHidden());
        assertFalse(sound.isPlaying());
    }
}
