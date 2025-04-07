package com.example.routing.dto.request;

import com.example.domain.enums.Direction;

/**
 * A request to set direction: e.g. { "direction": "LEFT" }
 */
public class DirectionRequest {
    private Direction direction;
    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }
}
