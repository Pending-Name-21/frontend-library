package com.bridge.renderHandler.render;

import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import java.util.List;

public record Frame(List<Sprite> sprites, List<Sound> sounds) {}
