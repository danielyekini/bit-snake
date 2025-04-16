package com.example.domain.model.snakeai;

import com.example.domain.enums.Direction;
import com.example.domain.model.Board;
import com.example.domain.model.Position;
import java.util.Random;

public class RandomSnake extends Snake {

    private final Random random;

    public RandomSnake(Position currentPosition, Direction currentDirection, int snakeId, String name) {
        super(currentPosition, currentDirection, snakeId, name);
        this.random = new Random();
    }

    /**
     * @param board the current board state
     * @return the direction the snake wants to move next
     */
    @Override
    public Direction getNextDirection(Board board) {
        Direction[] dirArray = new Direction[]{Direction.UP, Direction.DOWN,
        Direction.LEFT, Direction.RIGHT};
        return dirArray[random.nextInt(0, 4)];
    }
}
