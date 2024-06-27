package com.bridge.renderHandler.sprite;

/**
 * Coord class that represents the position of a sprite.
 */
public class Coord {

    private int x;
    private int y;
    private int z;

    /**
     * Constructor that initializes a coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate.
     *
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the z-coordinate.
     *
     * @return the z-coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Sets the z-coordinate.
     *
     * @param z the new y-coordinate
     */
    public void setZ(int z) {
        this.z = z;
    }
}
