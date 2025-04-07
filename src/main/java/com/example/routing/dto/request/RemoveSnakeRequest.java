package com.example.routing.dto.request;

/**
 * A request to remove a snake: e.g. { "snakeId": 3 }
 */
public class RemoveSnakeRequest {
    private int snakeId;
    public int getSnakeId() { return snakeId; }
    public void setSnakeId(int snakeId) { this.snakeId = snakeId; }
}
