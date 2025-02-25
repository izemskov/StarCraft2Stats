package ru.develgame.sc2stats.backend.dto.filter;

import lombok.Getter;

@Getter
public enum MatchDecision {
    NONE(null),
    WIN("Win"),
    LOSS("Loss");

    private final String entityValue;

    MatchDecision(String entityValue) {
        this.entityValue = entityValue;
    }
}
