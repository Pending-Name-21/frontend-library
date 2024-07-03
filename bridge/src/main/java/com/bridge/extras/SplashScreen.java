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

public class SplashScreen implements IIinitializerSubscriber, IUpdateSubscriber {
    private Sprite sprite;
    private Sound sound;
    private SoundRepository soundRepository;
    private SpriteRepository spriteRepository;
    private int framesCount;

    public SplashScreen(int framesCount) throws NonExistentFilePathException {
        this.framesCount = framesCount;

        spriteRepository = new SpriteRepository();
        SpriteBuilder spriteBuilder = new SpriteBuilder(spriteRepository);
        this.sprite = spriteBuilder.buildPath("").assemble();

        soundRepository = new SoundRepository();
        SoundBuilder soundBuilder = new SoundBuilder(soundRepository);
        this.sound = soundBuilder.buildPath("").assemble();
    }

    public void startAnimation() throws RenderException {
        Transmitter transmitter = new Transmitter(new SocketClient(SocketClient.NAMESPACE));
        Frame frame = new Frame(spriteRepository.retrieve(), soundRepository.retrieve());
        transmitter.send(frame);
    }

    @Override
    public void init() {
        try {
            startAnimation();
        } catch (RenderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifySubscriber() {
        if(framesCount >= 300){
            sprite.setHidden(true);
            sound.setPlaying(false);
        }
    }
}
