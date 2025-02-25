package ru.develgame.sc2stats.frontend.dto.filter;

import lombok.Getter;

@Getter
public enum MatchType {
    TYPE_NONE("Match type"),
    TYPE_1X1("1v1"),
    TYPE_2X2("2v2"),
    TYPE_3X3("3v3"),
    TYPE_CUSTOM("Custom");

    private final String displayName;

    MatchType(String displayName) {
        this.displayName = displayName;
    }
}
