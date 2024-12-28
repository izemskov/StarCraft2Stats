package ru.develgame.sc2stats.backend.dto.battlenet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BattleNetApiMatchesListResponseDto(@JsonProperty("matches") List<BattleNetApiMatchResponseDto> matches) {
}
