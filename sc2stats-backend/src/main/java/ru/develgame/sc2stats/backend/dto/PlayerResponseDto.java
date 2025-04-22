package ru.develgame.sc2stats.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PlayerResponseDto(@JsonProperty("totalCareerGames") int totalCareerGames,
                                @JsonProperty("best1v1FinishLeagueName") String best1v1FinishLeagueName,
                                @JsonProperty("best1v1FinishTimesAchieved") int best1v1FinishTimesAchieved,
                                @JsonProperty("bestTeamFinishLeagueName") String bestTeamFinishLeagueName,
                                @JsonProperty("bestTeamFinishTimesAchieved") int bestTeamFinishTimesAchieved,
                                @JsonProperty("currentMMR") int currentMMR,
                                @JsonProperty("bestMMR") int bestMMR,
                                @JsonProperty("currentMMR2x2") int currentMMR2x2,
                                @JsonProperty("bestMMR2x2") int bestMMR2x2,
                                @JsonProperty("currentMMR3x3") int currentMMR3x3,
                                @JsonProperty("bestMMR3x3") int bestMMR3x3) {
}
