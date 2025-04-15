package com.example.domain.model.snakeai;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domain.enums.Direction;
import com.example.domain.model.Board;
import com.example.domain.model.Position;

/**
 * Test class for the Snake abstract class.
 * Since Snake is abstract, we create a concrete implementation for testing.
 */
public class SnakeTest {

    // Mock Board for testing
    @Mock
    private Board mockBoard;

    // Concrete implementation of Snake for testing
    private static class TestSnake extends Snake {
        public TestSnake(Position start, Direction initialDirection, int snakeId, String name) {
            super(start, initialDirection, snakeId, name);
        }

        @Override
        public Direction getNextDirection(Board board) {
            // Simple implementation that always returns the current direction
            return this.direction;
        }
    }

    private TestSnake snake;
    private Position startPosition;
    private Direction initialDirection;
    private int snakeId;
    private String snakeName;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        startPosition = new Position(5, 5);
        initialDirection = Direction.RIGHT;
        snakeId = 1;
        snakeName = "TestSnake";

        // Create a test snake
        snake = new TestSnake(startPosition, initialDirection, snakeId, snakeName);
    }

    /**
     * Test the constructor
     */
    @Test
    void testConstructor() {
        // Verify that the snake was initialized correctly
        assertEquals(startPosition, snake.getHead());
        assertEquals(initialDirection, snake.getDirection());
        assertEquals(snakeId, snake.getSnakeId());
        assertEquals(snakeName, snake.getName());
        assertEquals(1, snake.getBody().size());
    }

    /**
     * Test the getNextDirection method
     */
    @Test
    void testGetNextDirection() {
        // The test implementation always returns the current direction
        assertEquals(initialDirection, snake.getNextDirection(mockBoard));
        
        // Change direction and verify
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getNextDirection(mockBoard));
    }

    /**
     * Test the updateBody method when not growing
     */
    @Test
    void testUpdateBodyNotGrowing() {
        // Initial state
        assertEquals(1, snake.getBody().size());
        assertEquals(startPosition, snake.getHead());
        
        // Update body with new head position
        Position newHead = new Position(6, 5);
        snake.updateBody(newHead, false);
        
        // Verify body was updated correctly
        assertEquals(1, snake.getBody().size());
        assertEquals(newHead, snake.getHead());
    }

    /**
     * Test the updateBody method when growing
     */
    @Test
    void testUpdateBodyGrowing() {
        // Initial state
        assertEquals(1, snake.getBody().size());
        assertEquals(startPosition, snake.getHead());
        
        // Update body with new head position and grow
        Position newHead = new Position(6, 5);
        snake.updateBody(newHead, true);
        
        // Verify body was updated correctly and grew
        assertEquals(2, snake.getBody().size());
        assertEquals(newHead, snake.getHead());
        
        // Verify the old head is now the second position
        assertEquals(startPosition, snake.getBody().get(1));
    }

    /**
     * Test the getBody method
     */
    @Test
    void testGetBody() {
        // Get the body
        List<Position> body = snake.getBody();
        
        // Verify body properties
        assertEquals(1, body.size());
        assertEquals(startPosition, body.get(0));
    }

    /**
     * Test the getHead method
     */
    @Test
    void testGetHead() {
        // Get the head
        Position head = snake.getHead();
        
        // Verify head is correct
        assertEquals(startPosition, head);
    }

    /**
     * Test the getDirection method
     */
    @Test
    void testGetDirection() {
        // Get the direction
        Direction direction = snake.getDirection();
        
        // Verify direction is correct
        assertEquals(initialDirection, direction);
    }

    /**
     * Test the setDirection method
     */
    @Test
    void testSetDirection() {
        // Set a new direction
        Direction newDirection = Direction.UP;
        snake.setDirection(newDirection);
        
        // Verify direction was updated
        assertEquals(newDirection, snake.getDirection());
    }

    /**
     * Test the getSnakeId method
     */
    @Test
    void testGetSnakeId() {
        // Get the snake ID
        int id = snake.getSnakeId();
        
        // Verify ID is correct
        assertEquals(snakeId, id);
    }

    /**
     * Test the getName method
     */
    @Test
    void testGetName() {
        // Get the snake name
        String name = snake.getName();
        
        // Verify name is correct
        assertEquals(snakeName, name);
    }
} 