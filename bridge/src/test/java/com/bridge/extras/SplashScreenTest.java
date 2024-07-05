package com.bridge.extras;

import static org.junit.jupiter.api.Assertions.*;

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
    void setUp() {
        spriteRepository = new SpriteRepository();
        soundRepository = new SoundRepository();
        splashScreen = new SplashScreen(spriteRepository, soundRepository, 0);
    }

    @Test
    void testConstructor() {
        assertNotNull(splashScreen);
        splashScreen.startAnimation();
        assertNotNull(splashScreen.getSprite());
        assertNotNull(splashScreen.getSound());
    }

    @Test
    void testStartAnimation() {
        splashScreen.startAnimation();
    }

    @Test
    void testNotifySubscriber() {
        splashScreen.startAnimation();
        assertFalse(splashScreen.getSprite().isHidden());
        assertFalse(splashScreen.getSound().isPlaying());

        splashScreen.setFramesCount(301);
        splashScreen.notifySubscriber();

        assertTrue(splashScreen.getSprite().isHidden());
        assertFalse(splashScreen.getSound().isPlaying());
    }

    @Test
    void testNotifySubscriberBeforeThreshold() {
        splashScreen.startAnimation();

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
