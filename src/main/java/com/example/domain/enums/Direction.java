package com.example.domain.enums;

import com.example.domain.model.Position;

/**
 * Basic directions for grid movement.
 */
public enum Direction {

    UP(1), DOWN(2), LEFT(3), RIGHT(4);
    /**
     * Helper to compute the next position given a current position and direction.
     * 
     * @param current The current (x, y) position.
     * @return A new Position that is one cell in this direction from 'current'.
     */
    public Position nextPosition(Position current) {
    }

    
}

