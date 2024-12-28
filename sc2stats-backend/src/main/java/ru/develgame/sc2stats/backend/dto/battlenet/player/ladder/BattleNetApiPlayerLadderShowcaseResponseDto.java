package ru.develgame.sc2stats.backend.dto.battlenet.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiPlayerLadderShowcaseResponseDto(@JsonProperty("ladderId") String ladderId,
                                                          @JsonProperty("localizedGameMode") String localizedGameMode) {
}
