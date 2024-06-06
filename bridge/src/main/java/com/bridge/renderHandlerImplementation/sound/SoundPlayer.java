package com.bridge.renderHandlerImplementation.sound;

import java.util.ArrayList;
import java.util.List;
import com.bridge.renderHandlerImplementation.sprite.*;


public class SoundPlayer {
    private List<Sound> sounds = new ArrayList<>();
    private SoundFactory soundFactory;

    public SoundPlayer(SoundFactory soundFactory) {
        this.soundFactory = soundFactory;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    public void removeSound(Sound sound) {
        sounds.remove(sound);
    }

    public void removeSoundFromSprite(Sprite sprite) {
        
    }

    public void playSound(Sprite sprite) {
       
    }

    public void playSound(Sound sound) {
        
    }
}
