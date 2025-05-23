package com.example.domain.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.domain.enums.Direction;
import com.example.domain.enums.SnakeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.example.domain.enums.GameMode;
import com.example.domain.model.Board;
import com.example.domain.model.Position;
import com.example.domain.model.snakeai.Snake;
import com.example.routing.dto.BoardStateDto;
import com.example.routing.events.BoardChangedEvent;

/**
 * Manages the core game logic (movement, collisions, food spawning, etc.).
 * It supports two game modes:
 *   1) add-as-you-go: snakes can be continuously added, game runs indefinitely.
 *   2) add-in-one-go: all snakes spawn at once, game ends when only one remains (or user ends it).
 */
public class GameService {

    // References to core components
    private final Board board;
    private final SnakeFactory snakeFactory;
    private Random random;

    // Main game timer that calls 'update()' periodically
    private Timer gameLoopTimer;

    // Tracks how many milliseconds have passed since last food was eaten
    // for the "spawn food if not eaten for 3 seconds" rule.

    // Indicates which game mode we're currently using
    private GameMode currentMode;
    private AtomicBoolean bool = new AtomicBoolean();

    // For add-in-one-go mode, we can track if a round is in progress
    private boolean roundInProgress;

    // Interval (ms) between game updates (ticks)
    private static final long UPDATE_INTERVAL_MS = 200;

    // For the "spawn additional food every 3 seconds" rule:
    private static final long FOOD_SPAWN_INTERVAL_MS = 3000;
    private long lastFoodSpawn = System.currentTimeMillis();

    // Minimum food items always present
    private static final int MIN_FOOD_COUNT = 3;

    private Snake userSnake;

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
        if(gameLoopTimer != null) throw new IllegalStateException("Game loop has already been started");
        gameLoopTimer = new Timer(true);
        gameLoopTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                update();
            }
        }, 0, UPDATE_INTERVAL_MS);

    }

    /**
     * Main update method called on each tick (~5 times per second if 200ms).
     * Moves snakes, checks collisions, spawns food, and ends rounds if needed.
     */
    public synchronized void update(){
        // 1. Move each snake
        updateSnakes();
    
        // 2. Ensure at least 3 pieces of food are on the board
        maintainMinimumFoodCount();
    
        // 3. Check if 3 seconds have passed since last food was eaten
        checkFoodSpawnTiming();
    
        // 4. If in add-in-one-go mode and round is in progress, check if we have <= 1 snake
        checkRoundEndConditions();

        // 5. Publish the board state
        BoardStateDto boardState = BoardStateDto.fromGameService(this);
        eventPublisher.publishEvent(new BoardChangedEvent(this, boardState));
    }

    /**
     * Update the direction of each snake.
     */
    private void updateSnakes() {
        for(Snake snake : board.getSnakes()) {
            snake.setDirection(snake.getNextDirection(board));
            snake.updateBody(snake.getDirection().nextPosition(snake.getHead()), false);
        }
    }

    /** 
     * Retrieve the direction for this snake. 
     * If it's controlled by a user, we read from userDirections; 
     * otherwise, we call the snake's AI (getNextDirection).
     */
    public void getSnakeDirection(){}


    /**
     * Ensure we always have at least MIN_FOOD_COUNT on the board.
     */
    public void maintainMinimumFoodCount() {if(board.getFoodPositions().size() < MIN_FOOD_COUNT) spawnRandomFood();}
    

    /**
     * Spawn additional food every 3 seconds.
     */
    public void checkFoodSpawnTiming(){
        if(System.currentTimeMillis() - lastFoodSpawn >= FOOD_SPAWN_INTERVAL_MS) {spawnRandomFood();}
    }
    

    /**
     * Spawn a piece of food in a random unoccupied cell.
     */
    public void spawnRandomFood() {
        Position pos;
        do {
            pos = new Position(random.nextInt(0, Board.WIDTH),
                               random.nextInt(0, Board.HEIGHT));
        } while (!board.addFood(pos));
        lastFoodSpawn = System.currentTimeMillis();
    }
    

    /**
     * Check if the round should end.
     */
    public void checkRoundEndConditions() {
        if(currentMode == GameMode.ADD_AS_YOU_GO && board.getSnakes().size() <= 1) {
            endRound();
        }
    }
    

    /* =========================
     *       Game Modes
     * ========================= */

    public GameMode getCurrentMode() {
        return currentMode;
    }

    public boolean isRoundInProgress() {
        return roundInProgress;
    }
    /**
     * Switch to add-as-you-go mode. 
     * In this mode, you can add snakes at any time.
     */
    public void switchToAddAsYouGoMode(){
        currentMode = GameMode.ADD_AS_YOU_GO;
    }

    /**
     * Switch to add-in-one-go mode. 
     * Typically you'd gather user config (how many BFS, A*, etc.), 
     * then call startRound(...) once you're ready.
     */
    public void switchToAddInOneGoMode(){
        currentMode = GameMode.ADD_IN_ONE_GO;
    }

    /**
     * Start a round in add-in-one-go mode.
     * 
     * @param snakeRequests a map or list describing which snakes to spawn 
     *                      (e.g. BFS=2, ASTAR=1, DIJKSTRA=0).
     */
    public void startRound(Map<SnakeType, Integer> snakeRequests) {
        if(currentMode == GameMode.ADD_IN_ONE_GO){
            clearBoard();
            for(SnakeType snakeType : snakeRequests.keySet())
                for(int num = 0; num < snakeRequests.get(snakeType); num++)
                    board.addSnake(snakeFactory.createSnake(snakeType, new Position(10, (int) (Math.random() * 10)), Direction.RIGHT, generateId()));
        }
    }
    

    /**
     * End the current round (in add-in-one-go mode). 
     * Possibly declare a winner if there's only one snake left.
     */
    public int endRound(){
        if(currentMode == GameMode.ADD_IN_ONE_GO){
            if(board.getSnakes().size() == 1){
                clearBoard();
                return board.getSnakes().getFirst().getSnakeId();
            }
            clearBoard();
        }
        return -1;
    }
    

    /** 
     * Utility to clear the board (snakes + food).
     */
    public void clearBoard(){
        for(Snake snake : board.getSnakes()){
            board.removeSnake(snake);
        }
        for(Position food : board.getFoodPositions()){
            board.removeFood(food);
        }
    }
    

    /* =========================
     *   Add / Remove Snakes
     * ========================= */

    /**
     * Add a new snake of a given type. 
     * For add-as-you-go mode, can be called any time. 
     * For add-in-one-go mode, should typically be called in 'startRound' before the round begins.
     */
    public void addSnake(SnakeType type){
        board.addSnake(snakeFactory.createSnake(type, new Position(0, 0), Direction.DOWN, generateId()));
    }
    

    /**
     * Remove a snake from the board (e.g., user action).
     */
    public void removeSnake(int snakeId){
        for(Snake snake : board.getSnakes()){
            if(snake.getSnakeId() == snakeId) board.removeSnake(snake);
        }
    }
    

    /**
     * For demonstration, a simple incremental ID generator.
     */
    public int generateId(){
        return board.getSnakes().size()-1;
    }
    

    /* =========================
     *   User Control
     * ========================= */

    /**
     * Take control of a snake by ID.
     * Only one snake can be controlled at a time.
     */
    public void controlSnake(int snakeId){
        for(Snake snake : board.getSnakes()){
            if(snake.getSnakeId() == snakeId){
                userSnake = snake;
                break;
            }
        }
    }
    

    /**
     * Release control of any snake currently controlled.
     */
    public void releaseControl(){
        userSnake = null; // ?
    }
    

    /**
     * Set the direction for the currently controlled snake.
     */
    public void setUserDirection(Direction newDirection){
        userSnake.setDirection(newDirection);
    }

    /* =========================
     *   Accessors
     * ========================= */
    

    /**
     * For the controller or UI to fetch the board state, e.g. for rendering.
     */
    public Board getBoard() {
        return board;
    }

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
