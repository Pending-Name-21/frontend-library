package com.bridge.renderHandler.sprite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SizeTest {
    private Size size;

    @BeforeEach
    void setUp() {
        size = new Size(10.0, 20.0);
    }

    @Test
    void testConstructor() {
        assertEquals(10.0, size.getHeight());
        assertEquals(20.0, size.getWidth());
    }

    @Test
    void testGetHeight() {
        assertEquals(10.0, size.getHeight());
    }

    @Test
    void testGetWidth() {
        assertEquals(20.0, size.getWidth());
    }

    @Test
    void testSetHeight() {
        size.setHeight(15.0);
        assertEquals(15.0, size.getHeight());
    }

    @Test
    void testSetWidth() {
        size.setWidth(25.0);
        assertEquals(25.0, size.getWidth());
    }

    @Test
    void testNegativeHeight() {
        size.setHeight(-5.0);
        assertEquals(-5.0, size.getHeight());
    }

    @Test
    void testNegativeWidth() {
        size.setWidth(-10.0);
        assertEquals(-10.0, size.getWidth());
    }

    @Test
    void testZeroHeight() {
        size.setHeight(0.0);
        assertEquals(0.0, size.getHeight());
    }

    @Test
    void testZeroWidth() {
        size.setWidth(0.0);
        assertEquals(0.0, size.getWidth());
    }
}
