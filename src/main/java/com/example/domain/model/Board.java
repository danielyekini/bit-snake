package com.example.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.domain.model.snakeai.Snake;

public class Board {
    // Static board dimensions (change as needed)
    public static final int WIDTH = 40;
    public static final int HEIGHT = 30;
    

    // Collection of snakes in the game
    private List<Snake> snakes;
    

    // Positions of all food items on the board
    private Set<Position> foodPositions;
    

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
        return List.copyOf(snakes);
    }

    /* =========================
     *  Food Management
     * ========================= */
    /**
     * Place a piece of food at the given position.
     * Returns false if the position was invalid or already occupied.
     */
     public boolean addFood(Position pos){
         if(isValidPosition(pos) && !isOccupied(pos, true)){
             foodPositions.add(pos);
             return true;
         }
         return false;
     }
    

    /**
     * Remove food at a given position if it exists.
     */
    public void removeFood(Position pos){ foodPositions.remove(pos); }

    /**
     * Check if there's food at the given position.
     */
    public boolean hasFood(Position p){ return foodPositions.contains(p) && !isOccupied(p); }

    /**
     * Get all the current food positions.
     */
    public Set<Position> getFoodPositions(){
        return Set.copyOf(foodPositions);
    }
    

    /* =========================
     *  Boundary & Collision
     * ========================= */
    /**
     * Check if a position is within the bounds of the board.
     */
    public boolean isValidPosition(Position pos){
        return (pos.x < WIDTH && pos.x >= 0) && (pos.y < HEIGHT && pos.y >= 0);
    }
    

    /**
     * Check if the given position is occupied by a snake segment.
     * This is useful to see if placing new food or moving a snake head
     * would result in a collision.
     */
    public boolean isOccupied(Position pos){
        for(Snake snake : snakes){
            for(Position segment : snake.getBody()){
                if(pos.equals(segment)) return true;
            }
        }
        return false;
    }

    public boolean isOccupied(Position pos, boolean food){
        boolean collideSnake = false;
        boolean collideFood = false;
        for(Snake snake : snakes){
            for(Position segment : snake.getBody()){
                if(pos.equals(segment)) collideSnake = true;
                break;
            }
        }
        for(Position foodPosition : foodPositions){
            if(pos.equals(foodPosition)) {
                collideFood = true;
                break;
            }
        }
        return collideSnake || collideFood;
    }
    

    /**
     * Determine if moving a snake’s head to 'newHeadPos' causes a collision
     * (out of bounds or overlaps any snake’s body).
     */
    public boolean isCollision(Position newHead, Snake snake){
        return !isValidPosition(newHead) || isOccupied(newHead);
    }


    /**
     * Helper method to move a snake on the board, if no collision.
     *  - This is optional. Some designs keep movement logic in GameService or Snake itself.
     * @param snake A snake object
     */
    public boolean moveSnake(Snake snake, Position newHead, boolean grow){
        if(!isCollision(newHead, snake) && isValidPosition(newHead)){
            snake.updateBody(newHead, grow);
            if(isOccupied(newHead, true)){
                removeFood(newHead);
            }
            return true;
        }
        return false;
    }

}
