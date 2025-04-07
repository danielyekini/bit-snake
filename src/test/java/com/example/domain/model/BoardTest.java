package com.example.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domain.enums.Direction;
import com.example.domain.model.snakeai.Snake;

public class BoardTest {

    private Board board;
    
    @Mock
    private Snake mockSnake;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        board = new Board();
    }

    @Test
    void testConstructor() {
        assertNotNull(board);
        assertTrue(board.getSnakes().isEmpty());
        assertTrue(board.getFoodPositions().isEmpty());
    }

    @Test
    void testAddSnake() {
        board.addSnake(mockSnake);
        List<Snake> snakes = board.getSnakes();
        assertEquals(1, snakes.size());
        assertTrue(snakes.contains(mockSnake));
    }

    @Test
    void testRemoveSnake() {
        board.addSnake(mockSnake);
        board.removeSnake(mockSnake);
        assertTrue(board.getSnakes().isEmpty());
    }

    @Test
    void testGetSnakesReturnsImmutableList() {
        board.addSnake(mockSnake);
        List<Snake> snakes = board.getSnakes();
        assertThrows(UnsupportedOperationException.class, () -> snakes.add(mockSnake));
    }

    @Test
    void testAddFoodValidPosition() {
        Position pos = new Position(5, 5);
        assertTrue(board.addFood(pos));
        assertTrue(board.hasFood(pos));
    }

    @Test
    void testAddFoodInvalidPosition() {
        // Test position outside board bounds
        Position invalidPos = new Position(Board.WIDTH + 1, Board.HEIGHT + 1);
        assertFalse(board.addFood(invalidPos));
        assertFalse(board.hasFood(invalidPos));
    }

    @Test
    void testAddFoodOccupiedPosition() {
        Position pos = new Position(5, 5);
        when(mockSnake.getBody()).thenReturn(List.of(pos));
        board.addSnake(mockSnake);
        
        assertFalse(board.addFood(pos));
        assertFalse(board.hasFood(pos));
    }

    @Test
    void testRemoveFood() {
        Position pos = new Position(5, 5);
        board.addFood(pos);
        board.removeFood(pos);
        assertFalse(board.hasFood(pos));
    }

    @Test
    void testGetFoodPositionsReturnsImmutableSet() {
        Position pos = new Position(5, 5);
        board.addFood(pos);
        Set<Position> foodPositions = board.getFoodPositions();
        assertThrows(UnsupportedOperationException.class, () -> foodPositions.add(new Position(6, 6)));
    }

    @Test
    void testIsValidPosition() {
        // Test valid positions
        assertTrue(board.isValidPosition(new Position(0, 0)));
        assertTrue(board.isValidPosition(new Position(Board.WIDTH - 1, Board.HEIGHT - 1)));
        
        // Test invalid positions
        assertFalse(board.isValidPosition(new Position(-1, 0)));
        assertFalse(board.isValidPosition(new Position(0, -1)));
        assertFalse(board.isValidPosition(new Position(Board.WIDTH, 0)));
        assertFalse(board.isValidPosition(new Position(0, Board.HEIGHT)));
    }

    @Test
    void testIsOccupied() {
        Position pos = new Position(5, 5);
        when(mockSnake.getBody()).thenReturn(List.of(pos));
        board.addSnake(mockSnake);
        
        assertTrue(board.isOccupied(pos));
        assertFalse(board.isOccupied(new Position(6, 6)));
    }

    @Test
    void testIsCollisionOutOfBounds() {
        Position outOfBounds = new Position(-1, 0);
        assertTrue(board.isCollision(outOfBounds, mockSnake));
    }

    @Test
    void testIsCollisionWithSnakeBody() {
        Position collisionPos = new Position(5, 5);
        when(mockSnake.getBody()).thenReturn(List.of(new Position(5, 5)));
        board.addSnake(mockSnake);
        
        assertTrue(board.isCollision(collisionPos, mockSnake));
    }

    @Test
    void testIsCollisionNoCollision() {
        Position safePos = new Position(5, 5);
        when(mockSnake.getBody()).thenReturn(List.of(new Position(6, 6)));
        board.addSnake(mockSnake);
        
        assertFalse(board.isCollision(safePos, mockSnake));
    }

    @Test
    void testMoveSnakeSuccessful() {
        Position startPos = new Position(5, 5);
        Position newHeadPos = new Position(5, 6);
        when(mockSnake.getBody()).thenReturn(List.of(startPos));
        board.addSnake(mockSnake);
        
        assertTrue(board.moveSnake(mockSnake, newHeadPos, false));
        verify(mockSnake).updateBody(newHeadPos, false);
    }

    @Test
    void testMoveSnakeWithCollision() {
        Position collisionPos = new Position(5, 5);
        when(mockSnake.getBody()).thenReturn(List.of(new Position(5, 5)));
        board.addSnake(mockSnake);
        
        assertFalse(board.moveSnake(mockSnake, collisionPos, false));
        verify(mockSnake, never()).updateBody(any(), anyBoolean());
    }

    @Test
    void testMoveSnakeWithFood() {
        Position startPos = new Position(5, 5);
        Position newHeadPos = new Position(5, 6);
        board.addFood(newHeadPos);
        when(mockSnake.getBody()).thenReturn(List.of(startPos));
        board.addSnake(mockSnake);
        
        assertTrue(board.moveSnake(mockSnake, newHeadPos, false));
        verify(mockSnake).updateBody(newHeadPos, false);
        assertFalse(board.hasFood(newHeadPos));
    }
} 