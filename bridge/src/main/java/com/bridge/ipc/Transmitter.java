package com.bridge.ipc;

import CoffeeTime.Output.Frame.*;
import com.google.flatbuffers.FlatBufferBuilder;
import java.util.List;

public class Transmitter {
    private final SocketClient socketClient;
    private FlatBufferBuilder builder;

    public Transmitter(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public void send(com.bridge.renderHandler.render.Frame frame) {
        builder = new FlatBufferBuilder();
        Frame.startFrame(builder);
        Frame.addSprites(builder, handleSprites(frame.sprites()));
        Frame.addSounds(builder, handleSounds(frame.sounds()));
        int serializedFrame = Frame.endFrame(builder);
        builder.finish(serializedFrame);
        socketClient.send(builder.dataBuffer());
    }

    private int handleSprites(List<com.bridge.renderHandler.sprite.Sprite> sprites) {
        int[] serializedSprites = new int[sprites.size()];
        for (int index = 0; index < sprites.size(); index++) {
            serializedSprites[index] = handleSprite(sprites.get(index));
        }
        return Frame.createSpritesVector(builder, serializedSprites);
    }

    private int handleSounds(List<com.bridge.renderHandler.sound.Sound> sounds) {
        int[] serializedSounds = new int[sounds.size()];
        for (int index = 0; index < sounds.size(); index++) {
            serializedSounds[index] = handleSound(sounds.get(index));
        }
        return Frame.createSoundsVector(builder, serializedSounds);
    }

    private int handleSound(com.bridge.renderHandler.sound.Sound sound) {
        int filePath = builder.createString(sound.getPath());
        return Sound.createSound(builder, sound.canPlay(), filePath);
    }

    private int handleSprite(com.bridge.renderHandler.sprite.Sprite sprite) {
        int filePath = builder.createString(sprite.getPath());
        return Sprite.createSprite(
                builder,
                handlePosition(sprite.getPosition()),
                handleSize(sprite.getSize()),
                sprite.isHidden(),
                filePath);
    }

    private int handlePosition(com.bridge.renderHandler.Coord coord) {
        return Coord.createCoord(builder, coord.getX(), coord.getY());
    }

    private int handleSize(com.bridge.renderHandler.Size size) {
        return Size.createSize(builder, size.getHeight(), size.getWidth());
    }
}
