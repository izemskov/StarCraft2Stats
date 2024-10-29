package ru.develgame.sc2stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BattleNetApiSC2MatchesListResponseDto(@JsonProperty("matches") List<BattleNetApiSC2MatchResponseDto> matches) {
}
