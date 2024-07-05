package com.bridge.renderHandler.builders;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.renderHandler.file.FileHandler;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Size;
import com.bridge.renderHandler.sprite.Sprite;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Builder class for creating Sprite objects.
 */
public class SpriteBuilder {
    private IRepository<Sprite> spriteRepository;
    private final FileHandler fileHandler;
    private Coord coord;
    private Size size;
    private Path filePath;

    /**
     * Constructs a SpriteBuilder with the specified repositories and file handler.
     *
     * @param spriteRepository the repository for storing sprites.
     */
    public SpriteBuilder(IRepository<Sprite> spriteRepository) {
        this.spriteRepository = spriteRepository;
        this.fileHandler = new FileHandler();
    }

    /**
     * Sets the coordinates for the Sprite.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return the current instance of SpriteBuilder.
     */
    public SpriteBuilder buildCoord(int x, int y) {
        coord = new Coord(x, y);
        return this;
    }

    /**
     * Sets the size for the Sprite.
     *
     * @param height the height of the Sprite.
     * @param width  the width of the Sprite.
     * @return the current instance of SpriteBuilder.
     */
    public SpriteBuilder buildSize(double height, double width) {
        size = new Size(height, width);
        return this;
    }

    /**
     * Sets the file path for the Sprite image.
     *
     * @param filePath the file path.
     * @return the current instance of SpriteBuilder.
     */
    public SpriteBuilder buildPath(String filePath) throws NonExistentFilePathException {
        this.filePath = fileHandler.getFilePath(filePath);
        return this;
    }

    /**
     * Sets the file path for the Sprite image from the screen files
     * without validating non-existing files
     *
     * @param filePath the file path.
     * @return the current instance of SpriteBuilder.
     */
    public SpriteBuilder buildPathFromScreenFiles(String filePath) {
        this.filePath = Paths.get(filePath);
        return this;
    }

    /**
     * Assembles the Sprite object from the set properties.
     *
     * @return the assembled Sprite.
     */
    public Sprite assemble() {
        Sprite sprite = new Sprite(coord, 0, size, filePath);
        spriteRepository.add(sprite);
        return sprite;
    }
}
