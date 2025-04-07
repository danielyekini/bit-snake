package com.example.routing.events;

import org.springframework.context.ApplicationEvent;
import com.example.routing.dto.BoardStateDto;

/**
 * A Spring ApplicationEvent that carries the new board state
 * whenever the GameService updates the board.
 */
public class BoardChangedEvent extends ApplicationEvent {

    private final BoardStateDto boardState;

    public BoardChangedEvent(Object source, BoardStateDto boardState) {
        super(source);
        this.boardState = boardState;
    }

    public BoardStateDto getBoardState() {
        return boardState;
    }
}

