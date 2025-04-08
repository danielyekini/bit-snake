package com.example.domain.service;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.routing.dto.BoardStateDto;
import com.example.routing.dto.request.ScoreboardEntry;
import com.example.routing.events.BoardChangedEvent;
import com.example.routing.events.ScoreboardChangedEvent;
import com.example.domain.enums.Direction;
import com.example.domain.enums.GameMode;
import com.example.domain.enums.SnakeType;
import com.example.domain.model.Board;
import com.example.domain.model.Position;
import com.example.domain.model.snakeai.Snake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Manages the core game logic (movement, collisions, food spawning, etc.).
 * It supports two game modes:
 *   1) add-as-you-go: snakes can be continuously added, game runs indefinitely.
 *   2) add-in-one-go: all snakes spawn at once, game ends when only one remains (or user ends it).
 */
public class GameService {

    // References to core components
    final Board board;
    final SnakeFactory snakeFactory;
    Random random;

    // Main game timer that calls 'update()' periodically

    Timer timer;
    TimerTask update = new TimerTask(){
        @Override
        public void run() {
            update();
        }
    };


    // Tracks how many milliseconds have passed since last food was eaten
    // for the "spawn food if not eaten for 3 seconds" rule.

    // Indicates which game mode we're currently using

    // For add-in-one-go mode, we can track if a round is in progress

    // Interval (ms) between game updates (ticks)
    private static final long UPDATE_INTERVAL_MS = 200;

    // For the "spawn additional food every 3 seconds" rule:
    private static final long FOOD_SPAWN_INTERVAL_MS = 3000;

    // Minimum food items always present
    private static final int MIN_FOOD_COUNT = 3;


    @Autowired
    private ApplicationEventPublisher eventPublisher;


    /**
     * Constructor
     */
    public GameService(Board board, SnakeFactory snakeFactory) {
        this.board = board;
        this.snakeFactory = snakeFactory;
        this.random = new Random();
    }

    /**
     * Start the continuous game loop (for both modes).
     * In "add-as-you-go", this runs indefinitely.
     * In "add-in-one-go", we start/stop rounds more explicitly but still use this timer.
     */
    public void startGameLoop(){
        if(timer != null) timer.scheduleAtFixedRate(update, 0, UPDATE_INTERVAL_MS);
        else throw new IllegalStateException("Already running");

    }

    /**
     * Main update method called on each tick (~5 times per second if 200ms).
     * Moves snakes, checks collisions, spawns food, and ends rounds if needed.
     */
    public void update(){
        for(Snake snake : board.snakes){

        }

    }

    
    /** 
     * Retrieve the direction for this snake. 
     * If it's controlled by a user, we read from userDirections; 
     * otherwise, we call the snake's AI (getNextDirection).
     */


    /**
     * For demonstration: ensure we always have at least MIN_FOOD_COUNT on the board.
     */
    

    /**
     * For demonstration: spawn additional food every 3 seconds.
     */
    

    /**
     * Spawn a piece of food in a random unoccupied cell.
     */
    public void spawnRandomFood() {
        Position pos;
        do {
            pos = new Position(random.nextInt(0, Board.WIDTH),
                    random.nextInt(0, Board.HEIGHT));
        } while (!board.addFood(pos));
    }
    

    /**
     * Check if the round should end.
     */
    

    /* =========================
     *       Game Modes
     * ========================= */

    /**
     * Switch to add-as-you-go mode. 
     * In this mode, you can add snakes at any time.
     */
    

    /**
     * Switch to add-in-one-go mode. 
     * Typically you'd gather user config (how many BFS, A*, etc.), 
     * then call startRound(...) once you're ready.
     */
    

    /**
     * Start a round in add-in-one-go mode.
     * 
     * @param snakeRequests a map or list describing which snakes to spawn 
     *                      (e.g. BFS=2, ASTAR=1, DIJKSTRA=0).
     */
    

    /**
     * End the current round (in add-in-one-go mode). 
     * Possibly declare a winner if there's only one snake left.
     */
    

    /** 
     * Utility to clear the board (snakes + food).
     */
    

    /* =========================
     *   Add / Remove Snakes
     * ========================= */

    /**
     * Add a new snake of a given type. 
     * For add-as-you-go mode, can be called any time. 
     * For add-in-one-go mode, should typically be called in 'startRound' before the round begins.
     */
//    public void addSnake(SnakeType type){
//        snakeFactory.createSnake(type)
//    }
    

    /**
     * Remove a snake from the board (e.g., user action).
     */
    

    /**
     * For demonstration, a simple incremental ID generator.
     */
    

    /* =========================
     *   User Control
     * ========================= */

    /**
     * Take control of a snake by ID.
     * Only one snake can be controlled at a time.
     */
    

    /**
     * Release control of any snake currently controlled.
     */
    

    /**
     * Set the direction for the currently controlled snake.
     */
    


    /* =========================
     *   Accessors
     * ========================= */
    

    /**
     * For the controller or UI to fetch the board state, e.g. for rendering.
     */
    

    /**
     * Cleanup
     */
    

    /**
     * Get the top X snakes by body length.
     * @param x the number of snakes to return
     * @return a list of the top X snakes
     */
    public List<Snake> getNSnakes(int x){
        return board.getSnakes().subList(0, x);
    }
    

    /* =========================
     *   Setters
     * ========================= */

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
}
