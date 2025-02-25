package ru.develgame.sc2stats.frontend.dto.filter;

import lombok.Getter;

@Getter
public enum MatchDecision {
    NONE("Match decision"),
    WIN("Win"),
    LOSS("Loss");

    private final String displayName;

    MatchDecision(String displayName) {
        this.displayName = displayName;
    }
}
