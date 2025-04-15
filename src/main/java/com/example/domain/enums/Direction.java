package com.example.domain.enums;

import com.example.domain.model.Position;

/**
 * Basic directions for grid movement.
 */
public enum Direction {

    UP(1), DOWN(2), LEFT(3), RIGHT(4), CELL_SIZE(20);


    Direction(int... args){
        
    }
    /**
     * Helper to compute the next position given a current position and direction.
     * 
     * @param current The current (x, y) position.
     * @return A new Position that is one cell in this direction from 'current'.
     */
    public Position nextPosition(Position current) {

        switch(this){
            case UP -> {
                return new Position(current.x, current.y - 1);
            }
            case DOWN -> {
                return new Position(current.x, current.y + 1);
            }
            case LEFT -> {
                return new Position(current.x - 1, current.y);
            }
            case RIGHT -> {
                return new Position(current.x + 1, current.y);
            }
            default -> {
                return current;
            }
        }
    }

    
}

