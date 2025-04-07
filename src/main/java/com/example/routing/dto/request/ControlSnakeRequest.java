package com.example.routing.dto.request;

/**
 * A request to control a snake: e.g. { "snakeId": 2 }
 */
public class ControlSnakeRequest {
    private int snakeId;
    public int getSnakeId() { return snakeId; }
    public void setSnakeId(int snakeId) { this.snakeId = snakeId; }
}
