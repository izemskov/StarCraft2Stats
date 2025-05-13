package ru.develgame.sc2stats.frontend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchesResponseDto(@JsonProperty("total") long total,
                                 @JsonProperty("matches") List<MatchResponseDto> matches) {
}
