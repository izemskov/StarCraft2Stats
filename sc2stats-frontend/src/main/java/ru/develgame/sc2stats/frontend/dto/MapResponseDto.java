package ru.develgame.sc2stats.frontend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MapResponseDto(@JsonProperty("name") String name,
                             @JsonProperty("type") String type,
                             @JsonProperty("wins") int wins,
                             @JsonProperty("losses") int losses,
                             @JsonProperty("winRate") int winRate) {
}
