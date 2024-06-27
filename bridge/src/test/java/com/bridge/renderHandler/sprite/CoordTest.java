package com.bridge.renderHandler.sprite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {
    private Coord coord;

    @BeforeEach
    void setUp() {
        coord = new Coord(5, 10,0);
    }

    @Test
    void testConstructor() {
        assertEquals(5, coord.getX());
        assertEquals(10, coord.getY());
    }

    @Test
    void testGetX() {
        assertEquals(5, coord.getX());
    }

    @Test
    void testGetY() {
        assertEquals(10, coord.getY());
    }

    @Test
    void testSetX() {
        coord.setX(15);
        assertEquals(15, coord.getX());
    }

    @Test
    void testSetY() {
        coord.setY(20);
        assertEquals(20, coord.getY());
    }

    @Test
    void testNegativeX() {
        coord.setX(-5);
        assertEquals(-5, coord.getX());
    }

    @Test
    void testNegativeY() {
        coord.setY(-10);
        assertEquals(-10, coord.getY());
    }

    @Test
    void testZeroX() {
        coord.setX(0);
        assertEquals(0, coord.getX());
    }

    @Test
    void testZeroY() {
        coord.setY(0);
        assertEquals(0, coord.getY());
    }
}
