package com.example.routing.dto.request;

import java.util.Map;

import com.example.domain.enums.SnakeType;

/**
 * A request to start a round with a map of snake types to counts:
 * { 
 *   "requests": { 
 *       "BFS": 2, 
 *       "ASTAR": 1 
 *   }
 * }
 */
public class StartRoundRequest {
    private Map<SnakeType, Integer> requests;
    public Map<SnakeType, Integer> getRequests() { return requests; }
    public void setRequests(Map<SnakeType, Integer> requests) { this.requests = requests; }
}