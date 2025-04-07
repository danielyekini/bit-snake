package com.example.domain.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.domain.model.Position;

/**
 * Test class for the Direction enum.
 */
public class DirectionTest {

    @Test
    void testNextPosition() {
        // Test with origin position
        Position origin = new Position(0, 0);
        
        // Test UP direction
        Position upPos = Direction.UP.nextPosition(origin);
        assertEquals(0, upPos.x);
        assertEquals(-1, upPos.y);
        
        // Test DOWN direction
        Position downPos = Direction.DOWN.nextPosition(origin);
        assertEquals(0, downPos.x);
        assertEquals(1, downPos.y);
        
        // Test LEFT direction
        Position leftPos = Direction.LEFT.nextPosition(origin);
        assertEquals(-1, leftPos.x);
        assertEquals(0, leftPos.y);
        
        // Test RIGHT direction
        Position rightPos = Direction.RIGHT.nextPosition(origin);
        assertEquals(1, rightPos.x);
        assertEquals(0, rightPos.y);
    }
    
    @Test
    void testNextPositionWithNonZeroPosition() {
        // Test with non-zero position
        Position pos = new Position(5, 10);
        
        // Test UP direction
        Position upPos = Direction.UP.nextPosition(pos);
        assertEquals(5, upPos.x);
        assertEquals(9, upPos.y);
        
        // Test DOWN direction
        Position downPos = Direction.DOWN.nextPosition(pos);
        assertEquals(5, downPos.x);
        assertEquals(11, downPos.y);
        
        // Test LEFT direction
        Position leftPos = Direction.LEFT.nextPosition(pos);
        assertEquals(4, leftPos.x);
        assertEquals(10, leftPos.y);
        
        // Test RIGHT direction
        Position rightPos = Direction.RIGHT.nextPosition(pos);
        assertEquals(6, rightPos.x);
        assertEquals(10, rightPos.y);
    }
    
    @Test
    void testNextPositionWithNegativePosition() {
        // Test with negative position
        Position pos = new Position(-5, -10);
        
        // Test UP direction
        Position upPos = Direction.UP.nextPosition(pos);
        assertEquals(-5, upPos.x);
        assertEquals(-11, upPos.y);
        
        // Test DOWN direction
        Position downPos = Direction.DOWN.nextPosition(pos);
        assertEquals(-5, downPos.x);
        assertEquals(-9, downPos.y);
        
        // Test LEFT direction
        Position leftPos = Direction.LEFT.nextPosition(pos);
        assertEquals(-6, leftPos.x);
        assertEquals(-10, leftPos.y);
        
        // Test RIGHT direction
        Position rightPos = Direction.RIGHT.nextPosition(pos);
        assertEquals(-4, rightPos.x);
        assertEquals(-10, rightPos.y);
    }
} 