package com.example.routing.events;

import org.springframework.context.ApplicationEvent;

import com.example.routing.dto.request.ScoreboardEntry;

import java.util.List;

/**
 * A Spring event that indicates the scoreboard has changed
 * and carries the updated scoreboard data.
 */
public class ScoreboardChangedEvent extends ApplicationEvent {

    private final List<ScoreboardEntry> scoreboard;

    public ScoreboardChangedEvent(Object source, List<ScoreboardEntry> scoreboard) {
        super(source);
        this.scoreboard = scoreboard;
    }

    public List<ScoreboardEntry> getScoreboard() {
        return scoreboard;
    }
}

