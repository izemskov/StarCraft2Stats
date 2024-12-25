package ru.develgame.sc2stats.dto.player.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiSC2BestFinishResponseDto(@JsonProperty("leagueName") String leagueName,
                                                   @JsonProperty("timesAchieved") int timesAchieved) {
}
