package com.bridge.piece;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import com.bridge.piece.render.RenderManager;
import com.bridge.piece.sound.*;
import com.bridge.piece.sprite.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class RenderManagerTest {

    private RenderManager renderManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        renderManager = new RenderManager();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testRenderSprites() {

        Sprite sprite1 = new Sprite(new Coord(0, 0), new Size(10, 10), "sprite1.png");
        Sprite sprite2 = new Sprite(new Coord(1, 1), new Size(20, 20), "sprite2.png");

        renderManager.spriteRenderer.addSprite(sprite1);
        renderManager.spriteRenderer.addSprite(sprite2);

        renderManager.renderSprites();

        assertEquals(
                "Rendering sprite: sprite1.png\nRendering sprite: sprite2.png\n",
                outContent.toString());
    }

    @Test
    public void testPlaySounds() {

        Sound sound1 =
                new Sound("sound1.wav") {
                    @Override
                    public boolean canPlay() {
                        return true;
                    }
                };
        Sound sound2 =
                new Sound("sound2.wav") {
                    @Override
                    public boolean canPlay() {
                        return true;
                    }
                };

        renderManager.soundPlayer.addSound(sound1);
        renderManager.soundPlayer.addSound(sound2);

        renderManager.playSounds();

        assertEquals(
                "Playing sound: sound1.wav\nPlaying sound: sound2.wav\n", outContent.toString());
    }
}
