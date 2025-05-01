package ru.develgame.sc2stats.frontend.dto.filter;

import lombok.Getter;

@Getter
public enum Actual {
    NONE("Actual (true/false)"),
    TRUE("True"),
    FALSE("False");

    private final String displayName;

    Actual(String displayName) {
        this.displayName = displayName;
    }
}
