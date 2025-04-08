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
            case ASTAR -> snake = new AStarSnake();
            case NEURAL -> snake = new NeuralNetSnake();
            case BFS -> snake = new BFSSnake();
            default -> throw new IllegalArgumentException("Unknown snake type");
        }
        snake.updateBody(startPos, true);
        snake.setDirection(dir);
        snake.setSnakeId(snakeId);
        return snake;
    }
    public String getRandomName(){
        return "";
    }
}
