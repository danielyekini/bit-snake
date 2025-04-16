package com.example.domain.enums;

public enum SnakeType {
    BFS(1), ASTAR(2), NEURAL(3), RANDOM(4);

    private final int snakeType;

    SnakeType(int snakeType){
        this.snakeType = snakeType;
    }
}
