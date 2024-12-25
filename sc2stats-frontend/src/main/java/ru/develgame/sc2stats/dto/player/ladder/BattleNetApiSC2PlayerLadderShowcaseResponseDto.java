package ru.develgame.sc2stats.dto.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiSC2PlayerLadderShowcaseResponseDto(@JsonProperty("ladderId") String ladderId,
                                                             @JsonProperty("localizedGameMode") String localizedGameMode) {
}
