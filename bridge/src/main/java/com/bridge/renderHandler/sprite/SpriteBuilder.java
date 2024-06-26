package com.bridge.renderHandler.sprite;

import com.bridge.renderHandler.file.FileHandler;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sprite.attributes.Coord;
import com.bridge.renderHandler.sprite.attributes.Size;

import java.nio.file.Path;

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
    public SpriteBuilder buildPath(String filePath) {
        this.filePath = fileHandler.getFilePath(filePath);
        return this;
    }

    /**
     * Assembles the Sprite object from the set properties.
     *
     * @return the assembled Sprite.
     */
    public Sprite assemble() {
        Sprite sprite = new Sprite(coord, size, filePath);
        spriteRepository.add(sprite);
        return sprite;
    }
}
