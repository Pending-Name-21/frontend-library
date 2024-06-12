package com.bridge.renderHandler;

/**
 * Size class that represents the size of a sprite.
 */
public class Size {

    private double height;
    private double width;

    public Size(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
