package com.bridge.ipc;

import CoffeeTime.Label.Label;
import CoffeeTime.Output.Frame.*;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.google.flatbuffers.FlatBufferBuilder;
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
    }

    /**
     * Serializes and sends a rendering frame over the socket connection.
     *
     * @param frame The rendering frame object containing sprites and sounds to transmit.
     */
    public void send(com.bridge.renderHandler.render.Frame frame) throws RenderException {
        socketClient.send(handleFrame(frame));
    }

    public void sendLabel(String text, String color, int x, int y) throws RenderException {
        socketClient.send(handleLabel(text, color, x, y));
    }

    private ByteBuffer handleLabel(String text, String color, int x, int y) throws RenderException {
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);

        // Create the text and color strings
        int textOffset = builder.createString(text);
        int colorOffset = builder.createString(color);

        // Create the position (Coord)
        int coordOffset = Coord.createCoord(builder, x, y);

        // Create the Label
        int labelOffset = Label.createLabel(builder, textOffset, colorOffset, coordOffset);

        // Finish the buffer
        builder.finish(labelOffset);

        // Get the ByteBuffer
        ByteBuffer buf = builder.dataBuffer();
        return buf;
    }

    private ByteBuffer handleFrame(com.bridge.renderHandler.render.Frame frame) {
        builder = new FlatBufferBuilder();
        int sprites = handleSprites(frame.sprites());
        int sounds = handleSounds(frame.sounds());
        Frame.startFrame(builder);
        Frame.addSprites(builder, sprites);
        Frame.addSounds(builder, sounds);
        int serializedFrame = Frame.endFrame(builder);
        builder.finish(serializedFrame);
        return builder.dataBuffer();
    }

    /**
     * Converts a list of sprites into their serialized form for FlatBuffers.
     *
     * @param sprites The list of Sprite objects to serialize.
     * @return The vector offset pointing to the serialized sprites in the FlatBuffer.
     */
    private int handleSprites(List<com.bridge.renderHandler.sprite.Sprite> sprites) {
        int[] serializedSprites = new int[sprites.size()];
        for (int index = 0; index < sprites.size(); index++) {
            serializedSprites[index] = handleSprite(sprites.get(index));
        }
        return Frame.createSpritesVector(builder, serializedSprites);
    }

    /**
     * Converts a list of sounds into their serialized form for FlatBuffers.
     *
     * @param sounds The list of Sound objects to serialize.
     * @return The vector offset pointing to the serialized sounds in the FlatBuffer.
     */
    private int handleSounds(List<com.bridge.renderHandler.sound.Sound> sounds) {
        int[] serializedSounds = new int[sounds.size()];
        for (int index = 0; index < sounds.size(); index++) {
            serializedSounds[index] = handleSound(sounds.get(index));
        }
        return Frame.createSoundsVector(builder, serializedSounds);
    }

    /**
     * Serializes a Sound object into FlatBuffer format.
     *
     * @param sound The Sound object to serialize.
     * @return The offset of the serialized Sound in the FlatBuffer.
     */
    private int handleSound(com.bridge.renderHandler.sound.Sound sound) {
        int filePath = builder.createString(sound.getPath().toString());
        return Sound.createSound(builder, sound.canPlay(), filePath);
    }

    /**
     * Serializes a Sprite object into FlatBuffer format.
     *
     * @param sprite The Sprite object to serialize.
     * @return The offset of the serialized Sprite in the FlatBuffer.
     */
    private int handleSprite(com.bridge.renderHandler.sprite.Sprite sprite) {
        int filePath = builder.createString(sprite.getPath().toString());
        return Sprite.createSprite(
                builder,
                handlePosition(sprite.getPosition()),
                handleSize(sprite.getSize()),
                sprite.isHidden(),
                filePath);
    }

    /**
     * Serializes a Coord object into FlatBuffer format.
     *
     * @param coord The Coord object to serialize.
     * @return The offset of the serialized Coord in the FlatBuffer.
     */
    private int handlePosition(com.bridge.renderHandler.sprite.Coord coord) {
        return Coord.createCoord(builder, coord.getX(), coord.getY());
    }

    /**
     * Serializes a Size object into FlatBuffer format.
     *
     * @param size The Size object to serialize.
     * @return The offset of the serialized Size in the FlatBuffer.
     */
    private int handleSize(com.bridge.renderHandler.sprite.Size size) {
        return Size.createSize(builder, size.getHeight(), size.getWidth());
    }
}
