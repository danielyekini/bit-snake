package com.example.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.domain.model.snakeai.Snake;

public class Board {
    // Static board dimensions (change as needed)
    static final int WIDTH = 40;
    static final int HEIGHT = 30;
    

    // Collection of snakes in the game
    public List<Snake> snakes;
    

    // Positions of all food items on the board
    public Set<Position> foodPositions;
    

    public Board() {
        this.snakes = new ArrayList<>();
        this.foodPositions = new HashSet<>();
    }

    /* =========================
     *  Snake Management
     * ========================= */
    /**
     * Add a new snake to the board.
     */
    public void addSnake(Snake snake){
        snakes.add(snake);
    }

    /**
     * Remove a snake (e.g., if it's dead).
     */
    public void removeSnake(Snake snake){
        snakes.remove(snake);
    }

    /**
     * Return an immutable view of all snakes.
     */
    public List<Snake> getSnakes(){
        List<Snake> snakesCopy = snakes;
        return snakesCopy;
    }

    /* =========================
     *  Food Management
     * ========================= */
    /**
     * Place a piece of food at the given position.
     * Returns false if the position was invalid or already occupied.
     */
     public boolean addFood(Position pos){
         // For now
         foodPositions.add(pos);
         return true;
     }
    

    /**
     * Remove food at a given position if it exists.
     */
    public void removeFood(Position pos){
        foodPositions.remove(pos);
    }

    /**
     * Check if there's food at the given position.
     */
    public boolean hasFood(Position p){ return foodPositions.contains(p); }

    /**
     * Get all the current food positions.
     */
    public Set<Position> getFoodPositions(){
        Set<Position> foodsCopy = foodPositions;
        return foodsCopy;
    }
    

    /* =========================
     *  Boundary & Collision
     * ========================= */
    /**
     * Check if a position is within the bounds of the board.
     */
    public boolean isValidPosition(Position pos){
        return (pos.x >= WIDTH || pos.x < 0) && (pos.y >= HEIGHT || pos.y < 0);
    }
    

    /**
     * Check if the given position is occupied by a snake segment.
     * This is useful to see if placing new food or moving a snake head
     * would result in a collision.
     */
    

    /**
     * Determine if moving a snake’s head to 'newHeadPos' causes a collision
     * (out of bounds or overlaps any snake’s body).
     */


    /**
     * Helper method to move a snake on the board, if no collision.
     *  - This is optional. Some designs keep movement logic in GameService or Snake itself.
     */

}
