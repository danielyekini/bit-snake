package com.example.domain.enums;

public enum SnakeType {
    BFS(1), ASTAR(2), NEURAL(3);

    private final int snakeType;

    SnakeType(int snakeType){
        this.snakeType = snakeType;
    }
}
