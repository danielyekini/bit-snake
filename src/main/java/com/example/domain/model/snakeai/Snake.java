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

    protected LinkedList<Position> body;
    protected Direction direction;
    protected int snakeId;
    protected String name;

    public Snake(int snakeId, String name) {
        body = new LinkedList<>();
        this.snakeId = snakeId;
        this.name = name;
    }

    /**
     * Each subclass (AStarSnake, BFSSnake, etc.) implements
     * its own logic to decide the next direction.
     *
     * @param board the current board state
     * @return the direction the snake wants to move next
     */
    protected abstract Direction getNextDirection(Board board);
    

    /**
     * Update the snake's body based on a new head position.
     * @param newHead the new head position
     * @param grow if true, the snake length increases by 1 (no tail removal)
     */
    public void updateBody(Position newHead, boolean grow){
        body.addFirst(newHead);
        if(!grow){
            for(int segment = body.size() - 1; segment > 0; segment--){
                body.set(segment, body.get(segment - 1));
            }
        } else{
            body.addLast(body.getLast());
            for(int segment = body.size() - 2; segment > 0; segment--){
                body.set(segment, body.get(segment - 1));
            }
        }
    }
    

    /**
     * @return the entire body of the snake (head is at index 0)
     */
    public LinkedList<Position> getBody(){return body;}
    

    /**
     * @return the snake's head position (the front of the list)
     */
    public Position getHead(){return body.getFirst();}
    

    /**
     * @return the snake's current direction
     */
    public Direction getDirection(){return direction;}
    

    /**
     * Sets the snake's current direction
     */
    public void setDirection(Direction direction){this.direction = direction;}
    

    /**
     * @return an ID for the snake (optional for your usage)
     */
    public int getSnakeId(){return snakeId;}

    /**
     * Sets the snake ID
     * @param snakeId new snake ID
     */
    public void setSnakeId(int snakeId){this.snakeId = snakeId;}


    /**
     * @return the name of the snake
     */
    public String getName(){return name;}


    /**
     * Determine the Direction needed to move from 'from' -> 'to' (which must be adjacent).
     */
    private Direction directionFromTo(Position from, Position to) {
        return null;
    }
    
}
