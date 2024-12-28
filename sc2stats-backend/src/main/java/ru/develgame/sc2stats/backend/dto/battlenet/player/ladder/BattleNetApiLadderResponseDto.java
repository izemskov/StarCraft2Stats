package ru.develgame.sc2stats.backend.dto.battlenet.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BattleNetApiLadderResponseDto(
        @JsonProperty("ladderTeams") List<BattleNetApiLadderTeamResponseDto> ladderTeams) {
}
