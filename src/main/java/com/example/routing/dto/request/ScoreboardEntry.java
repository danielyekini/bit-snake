package com.example.routing.dto.request;

/**
 * Scoreboard entry for top X snakes.
 */
public class ScoreboardEntry {
    private int snakeId;
    private int length;
    private String name;
    public ScoreboardEntry(int snakeId, int length, String name) {
        this.snakeId = snakeId;
        this.length = length;
        this.name = name;
    }
    
    public int getSnakeId() { return snakeId; }
    public int getLength() { return length; }
    public String getName()  { return name; }
}
