package ru.develgame.sc2stats.backend.dto.battlenet.player.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiBestFinishResponseDto(@JsonProperty("leagueName") String leagueName,
                                                @JsonProperty("timesAchieved") int timesAchieved) {
}
