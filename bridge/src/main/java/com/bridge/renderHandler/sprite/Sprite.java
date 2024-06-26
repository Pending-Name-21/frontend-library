package com.bridge.renderHandler.sprite;

import com.bridge.renderHandler.file.IFile;
import java.nio.file.Path;

/**
 * Represents a graphical sprite with position, size, visibility, and file path.
 */
public class Sprite implements IFile {
    private Coord position;
    private Size size;
    private boolean isHidden;
    private final Path path;

    /**
     * Constructs a Sprite with specified position, size, and path.
     *
     * @param position the coordinates of the sprite.
     * @param size the size of the sprite.
     * @param path the file path of the sprite image.
     */
    public Sprite(Coord position, Size size, Path path) {
        this.position = position;
        this.size = size;
        this.path = path;
        this.isHidden = false;
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
     * Gets the sprite file path.
     *
     * @return the file path
     */
    @Override
    public Path getPath() {
        return this.path;
    }
}
