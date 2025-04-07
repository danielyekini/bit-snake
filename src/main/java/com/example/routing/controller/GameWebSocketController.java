package com.example.routing.controller;

import com.example.domain.enums.SnakeType;
import com.example.domain.service.GameService;
import com.example.domain.enums.GameMode;
import com.example.routing.dto.request.DirectionRequest;
import com.example.routing.dto.request.ControlSnakeRequest;
import com.example.routing.dto.request.RemoveSnakeRequest;
import com.example.routing.dto.request.SnakeTypeRequest;
import com.example.routing.dto.request.StartRoundRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * A STOMP-based WebSocket controller for handling real-time
 * messages from the client (add snake, remove snake, start/end round, etc.)
 * and broadcasting updated game state as needed.
 */
@Controller
public class GameWebSocketController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameWebSocketController(GameService gameService, 
                                   SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    // ----------------------------------------------------------------------
    // 1) Basic Snake Actions: Add / Remove / Control / Release / Direction
    // ----------------------------------------------------------------------

    /**
     * The client sends a message to /app/addSnake with a JSON body specifying which snake type to add.
     * For example: { "type": "BFS" }
     * 
     * The client must define a matching DTO or can simply send a SnakeType string.
     */
    @MessageMapping("/addSnake")
    public void handleAddSnake(SnakeTypeRequest request) {
        // e.g., BFS, ASTAR, DIJKSTRA
        gameService.addSnake(request.getType());
        // Optionally broadcast updated board or scoreboard
        broadcastBoardState();
        broadcastScoreboard(5); // e.g. top 5
    }

    /**
     * Remove a snake by ID. The client might send:
     * { "snakeId": 3 }
     */
    @MessageMapping("/removeSnake")
    public void handleRemoveSnake(RemoveSnakeRequest request) {
        gameService.removeSnake(request.getSnakeId());
        broadcastBoardState();
        broadcastScoreboard(5);
    }

    /**
     * The user wants to take control of a particular snake.
     * { "snakeId": 2 }
     */
    @MessageMapping("/controlSnake")
    public void handleControlSnake(ControlSnakeRequest request) {
        gameService.controlSnake(request.getSnakeId());
        // Possibly broadcast which snake is controlled, if relevant
        broadcastBoardState();
    }

    /**
     * The user releases control of any snake currently controlled.
     */
    @MessageMapping("/releaseControl")
    public void handleReleaseControl() {
        gameService.releaseControl();
        broadcastBoardState();
    }

    /**
     * The user sets a direction for the currently controlled snake.
     * For example: { "direction": "LEFT" }
     */
    @MessageMapping("/setDirection")
    public void handleSetDirection(DirectionRequest request) {
        // e.g., "UP", "DOWN", "LEFT", "RIGHT"
        gameService.setUserDirection(request.getDirection());
        // Usually no immediate board broadcast is needed, 
        // because we'll push updates on the next game tick
    }

    // ----------------------------------------------------------------------
    // 2) Game Mode & Round Management
    // ----------------------------------------------------------------------

    /**
     * The client wants to start the game loop.
     */
    @MessageMapping("/startGame")
    public void handleStartGame() {
        System.out.println("\n\nController recieved startGame message, passing to service\n\n");
        // Start the loop if not already started
        gameService.startGameLoop();
        // Possibly broadcast an immediate board state if you want
        // but the loop will broadcast every 200ms anyway
    }

    /**
     * The client wants to switch to "add-as-you-go" mode.
     * We can only do this if a round is not in progress or after stopping a round.
     */
    @MessageMapping("/switchToAddAsYouGo")
    public void handleSwitchToAddAsYouGo() {
        // Possibly check if we are in a round
        if (gameService.isRoundInProgress()) {
            // Optionally ignore or send an error message
            return;
        }
        gameService.switchToAddAsYouGoMode();
    }

    /**
     * The client wants to switch to "add-in-one-go" mode.
     * No round is started yet; it just sets the mode.
     */
    @MessageMapping("/switchToAddInOneGo")
    public void handleSwitchToAddInOneGo() {
        // If a round is in progress, ignore or handle
        if (gameService.isRoundInProgress()) {
            return;
        }
        gameService.switchToAddInOneGoMode();
    }

    /**
     * Start a round in add-in-one-go mode.
     * The client might send a JSON map describing the number of BFS, ASTAR, etc.
     * For example: 
     * {
     *   "requests": {
     *       "BFS": 2,
     *       "ASTAR": 1
     *   }
     * }
     */
    @MessageMapping("/startRound")
    public void handleStartRound(StartRoundRequest request) {
        // If we're not in add-in-one-go or a round is active, ignore or handle
        if (gameService.getCurrentMode() != GameMode.ADD_IN_ONE_GO || 
            gameService.isRoundInProgress()) {
            return;
        }
        // The request might hold a Map<SnakeType, Integer>
        Map<SnakeType, Integer> snakeRequests = request.getRequests();
        gameService.startRound(snakeRequests);
    }

    /**
     * End the current round in add-in-one-go mode.
     * Once the round ends, the system reverts to add-as-you-go automatically.
     */
    @MessageMapping("/endRound")
    public void handleEndRound() {
        if (gameService.getCurrentMode() == GameMode.ADD_IN_ONE_GO && 
            gameService.isRoundInProgress()) {
            gameService.endRound();
            // The gameService might switch back to add-as-you-go
            gameService.switchToAddAsYouGoMode();
        }
    }

    private void broadcastBoardState() {
        // Implementation of broadcastBoardState method
    }

    private void broadcastScoreboard(int topN) {
        // Implementation of broadcastScoreboard method
    }
}

