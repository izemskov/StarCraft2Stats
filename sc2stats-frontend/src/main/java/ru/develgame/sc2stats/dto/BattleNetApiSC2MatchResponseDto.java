package ru.develgame.sc2stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiSC2MatchResponseDto(@JsonProperty("map") String map,
                                              @JsonProperty("type") String type,
                                              @JsonProperty("decision") String decision,
                                              @JsonProperty("speed") String speed,
                                              @JsonProperty("date") Long date) {
}
