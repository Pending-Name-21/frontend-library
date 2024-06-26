package com.bridge.renderHandler.sprite.attributes;

/**
 * Size class that represents the size of a sprite.
 */
public class Size {

    private double height;
    private double width;

    /**
     * Constructs a Size object with the specified height and width.
     *
     * @param height the height of the sprite
     * @param width the width of the sprite
     */
    public Size(double height, double width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Returns the height of the sprite.
     *
     * @return the height of the sprite
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the width of the sprite.
     *
     * @return the width of the sprite
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Sets the height of the sprite.
     *
     * @param height the new height of the sprite
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Sets the width of the sprite.
     *
     * @param width the new width of the sprite
     */
    public void setWidth(double width) {
        this.width = width;
    }
}
