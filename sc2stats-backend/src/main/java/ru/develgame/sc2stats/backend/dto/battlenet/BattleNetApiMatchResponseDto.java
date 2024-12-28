package ru.develgame.sc2stats.backend.dto.battlenet;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiMatchResponseDto(@JsonProperty("map") String map,
                                           @JsonProperty("type") String type,
                                           @JsonProperty("decision") String decision,
                                           @JsonProperty("speed") String speed,
                                           @JsonProperty("date") Long date) {
}
