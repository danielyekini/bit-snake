package com.example.routing.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.example.routing.events.ScoreboardChangedEvent;

@Component
public class ScoreboardEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Listens for ScoreboardChangedEvent and sends the scoreboard to /topic/scoreboard.
     */
    @EventListener
    public void onScoreboardChanged(ScoreboardChangedEvent event) {
        var scoreboard = event.getScoreboard();
        messagingTemplate.convertAndSend("/topic/scoreboard", scoreboard);
    }
}

