package com.bridge.renderHandler.render;

import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import java.util.List;

/**
 * Represents a rendering frame containing sprites and sounds.
 * This class is immutable and serves as a data container.
 *
 */
public record Frame(List<Sprite> sprites, List<Sound> sounds) {}
