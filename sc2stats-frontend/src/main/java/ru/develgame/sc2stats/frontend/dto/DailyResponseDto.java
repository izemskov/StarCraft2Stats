package ru.develgame.sc2stats.frontend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DailyResponseDto(@JsonProperty("type") String type,
                               @JsonProperty("wins") int wins,
                               @JsonProperty("loses") int losses,
                               @JsonProperty("date") String date) {
}
