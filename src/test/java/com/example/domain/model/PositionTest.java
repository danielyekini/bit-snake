package com.example.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Position class.
 */
public class PositionTest {

    @Test
    void testConstructor() {
        // Test with positive coordinates
        Position pos1 = new Position(5, 10);
        assertEquals(5, pos1.x);
        assertEquals(10, pos1.y);

        // Test with negative coordinates
        Position pos2 = new Position(-3, -7);
        assertEquals(-3, pos2.x);
        assertEquals(-7, pos2.y);

        // Test with zero coordinates
        Position pos3 = new Position(0, 0);
        assertEquals(0, pos3.x);
        assertEquals(0, pos3.y);
    }

    @Test
    void testEquals() {
        // Same object
        Position pos1 = new Position(5, 10);
        assertTrue(pos1.equals(pos1));

        // Equal objects
        Position pos2 = new Position(5, 10);
        assertTrue(pos1.equals(pos2));
        assertTrue(pos2.equals(pos1));

        // Different objects
        Position pos3 = new Position(5, 11);
        assertFalse(pos1.equals(pos3));
        assertFalse(pos3.equals(pos1));

        // Different x, same y
        Position pos4 = new Position(6, 10);
        assertFalse(pos1.equals(pos4));
        assertFalse(pos4.equals(pos1));

        // Null object
        assertFalse(pos1.equals(null));

        // Different type
        assertFalse(pos1.equals("not a Position"));
    }

    @Test
    void testHashCode() {
        // Equal objects should have equal hash codes
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(5, 10);
        assertEquals(pos1.hashCode(), pos2.hashCode());

        // Different objects should have different hash codes
        Position pos3 = new Position(5, 11);
        assertNotEquals(pos1.hashCode(), pos3.hashCode());
    }

    @Test
    void testToString() {
        // Test with positive coordinates
        Position pos1 = new Position(5, 10);
        assertEquals("(5, 10)", pos1.toString());

        // Test with negative coordinates
        Position pos2 = new Position(-3, -7);
        assertEquals("(-3, -7)", pos2.toString());

        // Test with zero coordinates
        Position pos3 = new Position(0, 0);
        assertEquals("(0, 0)", pos3.toString());
    }
} 