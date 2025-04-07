package com.example.routing.dto;

import java.util.List;

import com.example.domain.enums.Direction;

/**
 * Example snake data structure for use inside BoardStateDto.
 */
public class SnakeDto {
    private int snakeId;
    private String name;
    private List<?> bodyPositions; // or a List<Position> 

    public SnakeDto(int snakeId, String name, List<?> bodyPositions) {
        this.snakeId = snakeId;
        this.name = name;
        this.bodyPositions = bodyPositions;
    }

    public int getSnakeId() { return snakeId; }
    public String getName() { return name; }
    public List<?> getBodyPositions() { return bodyPositions; }
}
