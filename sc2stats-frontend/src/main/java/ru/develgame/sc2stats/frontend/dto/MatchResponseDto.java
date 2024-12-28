package ru.develgame.sc2stats.frontend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MatchResponseDto(@JsonProperty("map") String map,
                               @JsonProperty("type") String type,
                               @JsonProperty("decision") String decision,
                               @JsonProperty("date") long date) {
}
