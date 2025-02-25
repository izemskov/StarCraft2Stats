package ru.develgame.sc2stats.backend.dto.filter;

import lombok.Getter;

@Getter
public enum MatchType {
    TYPE_NONE(null),
    TYPE_1X1("1v1"),
    TYPE_2X2("2v2"),
    TYPE_3X3("3v3"),
    TYPE_CUSTOM("Custom");

    private final String entityValue;

    MatchType(String entityValue) {
        this.entityValue = entityValue;
    }
}
