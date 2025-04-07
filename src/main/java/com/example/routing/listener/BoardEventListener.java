package com.example.routing.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.example.routing.events.BoardChangedEvent;

@Component
public class BoardEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * This method is called every time a BoardChangedEvent is published.
     */
    @EventListener
    public void onBoardChanged(BoardChangedEvent event) {
        // The event has the BoardStateDto
        var boardState = event.getBoardState();

        // Broadcast the board state to /topic/board
        messagingTemplate.convertAndSend("/topic/board", boardState);

        // If you also want scoreboard info, you can do it here too:
        // messagingTemplate.convertAndSend("/topic/scoreboard", scoreboardDto);
    }
}
