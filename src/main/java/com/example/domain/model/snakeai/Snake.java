package com.example.domain.model.snakeai;

import java.util.LinkedList;
import java.util.List;

import com.example.domain.enums.Direction;
import com.example.domain.model.Board;
import com.example.domain.model.Position;

public abstract class Snake {
    // A list of the snake's body positions; head is at index 0

    // The current direction the snake is moving

    // Optional ID if you have multiple snakes

    // The name of the snake


    public Snake() {
    }

    /**
     * Each subclass (AStarSnake, BFSSnake, etc.) implements 
     * its own logic to decide the next direction.
     *
     * @param board the current board state
     * @return the direction the snake wants to move next
     */
    

    /**
     * Update the snake's body based on a new head position.
     * @param newHead the new head position
     * @param grow if true, the snake length increases by 1 (no tail removal)
     */
    

    /**
     * @return the entire body of the snake (head is at index 0)
     */
    

    /**
     * @return the snake's head position (the front of the list)
     */
    

    /**
     * @return the snake's current direction
     */
    

    /**
     * Sets the snake's current direction
     */
    

    /**
     * @return an ID for the snake (optional for your usage)
     */
    

    /**
     * @return the name of the snake
     */


    /**
     * Determine the Direction needed to move from 'from' -> 'to' (which must be adjacent).
     */
    private Direction directionFromTo(Position from, Position to) {
    }
    
}
