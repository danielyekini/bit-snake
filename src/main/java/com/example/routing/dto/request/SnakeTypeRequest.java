package com.example.routing.dto.request;

import com.example.domain.enums.SnakeType;

/**
 * A request to add a snake: e.g. { "type": "BFS" }
 */
public class SnakeTypeRequest {
    private SnakeType type;
    public SnakeType getType() { return type; }
    public void setType(SnakeType type) { this.type = type; }
}
