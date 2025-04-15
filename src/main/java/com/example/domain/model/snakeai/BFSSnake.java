package com.example.domain.model.snakeai;

import java.util.*;

import com.example.domain.enums.Direction;
import com.example.domain.model.Board;
import com.example.domain.model.Position;

public class BFSSnake extends Snake {

    public BFSSnake(Position currentPosition, Direction currentDirection, int snakeId, String name) {
        super(currentPosition, currentDirection, snakeId, name);

    }

    @Override
    public Direction getNextDirection(Board board) {
        return null;
    }
}

