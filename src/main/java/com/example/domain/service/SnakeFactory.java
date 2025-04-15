package com.example.domain.service;

import com.example.domain.enums.Direction;
import com.example.domain.enums.SnakeType;
import com.example.domain.model.Position;
import com.example.domain.model.snakeai.*;

public class SnakeFactory {

    public SnakeFactory(){

    }
    public Snake createSnake(SnakeType type, Position startPos, Direction dir, int snakeId){
        Snake snake;
        switch(type){
            case ASTAR -> snake = new AStarSnake(startPos, dir, snakeId, getRandomName());
            case NEURAL -> snake = new NeuralNetSnake(startPos, dir, snakeId, getRandomName());
            case BFS -> snake = new BFSSnake(startPos, dir, snakeId, getRandomName());
            default -> throw new IllegalArgumentException("Unknown snake type.");
        }
        snake.setDirection(dir);
        snake.setSnakeId(snakeId);
        return snake;
    }
    public String getRandomName(){
        return "";
    }
}
