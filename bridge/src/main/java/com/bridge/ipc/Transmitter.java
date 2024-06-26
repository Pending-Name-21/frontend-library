package com.bridge.ipc;

import CoffeeTime.Output.Frame.*;
import com.google.flatbuffers.FlatBufferBuilder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * The Transmitter class handles the serialization and transmission of rendering frames
 * using FlatBuffers over a socket connection.
 */
public class Transmitter {

    private final SocketClient socketClient;
    private FlatBufferBuilder builder;

    /**
     * Constructs a Transmitter object with a specified SocketClient for communication.
     *
     * @param socketClient The SocketClient instance used for sending data.
     */
    public Transmitter(SocketClient socketClient) {
        this.socketClient = socketClient;
        this.builder = new FlatBufferBuilder(1024);
    }

    /**
     * Serializes and sends a rendering frame over the socket connection.
     *
     * @param frame The rendering frame object containing sprites and sounds to transmit.
     */
    public void send(com.bridge.renderHandler.render.Frame frame) throws IOException, InterruptedException {
        builder.clear();
        int sprites = handleSprites(frame.sprites());
        int sounds = handleSounds(frame.sounds());
        Frame.startFrame(builder);
        Frame.addSprites(builder, sprites);
        Frame.addSounds(builder, sounds);
        int serializedFrame = Frame.endFrame(builder);
        builder.finish(serializedFrame);
        ByteBuffer buffer = builder.dataBuffer();
        socketClient.send(buffer);
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
