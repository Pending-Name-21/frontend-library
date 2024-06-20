package com.bridge.renderHandler.sprite;

import com.bridge.renderHandler.*;
import com.bridge.renderHandler.sound.*;

/**
 * Sprite class that represents a sprite with position, size and path attributes.
 */
public class Sprite implements IFile {

    private Coord position;
    private Size size;
    private boolean isHidden;
    private Sound sound;
    private String path;

    /**
     * Constructor that initializes a sprite.
     *
     * @param position the position of the sprite
     * @param size the size of the sprite
     * @param path the file path of the sprite
     */
    public Sprite(Coord position, Size size, String path) {
        this.position = position;
        this.size = size;
        this.path = path;
    }

    /**
     * Gets the position of the sprite.
     *
     * @return the position of the sprite
     */
    public Coord getPosition() {
        return position;
    }

    /**
     * Sets the position of the sprite.
     *
     * @param position the new position of the sprite
     */
    public void setPosition(Coord position) {
        this.position = position;
    }

    /**
     * Gets the size of the sprite.
     *
     * @return the size of the sprite
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the sprite.
     *
     * @param size the new size of the sprite
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Checks if the sprite is hidden.
     *
     * @return true if the sprite is hidden, false otherwise
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * Sets the visibility of the sprite.
     *
     * @param hidden true to hide the sprite, false to show it
     */
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * Gets the sound associated with the sprite.
     *
     * @return the sound associated with the sprite
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Sets the sound associated with the sprite.
     *
     * @param sound the new sound associated with the sprite
     */
    public void setSound(Sound sound) {
        this.sound = sound;
    }

    @Override
    public String getPath() {
        return this.path;
}
}
