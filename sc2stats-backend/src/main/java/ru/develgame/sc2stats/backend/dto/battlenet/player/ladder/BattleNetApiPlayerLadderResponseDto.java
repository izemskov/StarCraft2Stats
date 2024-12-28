package ru.develgame.sc2stats.backend.dto.battlenet.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BattleNetApiPlayerLadderResponseDto(
        @JsonProperty("allLadderMemberships") List<BattleNetApiPlayerLadderShowcaseResponseDto> allLadderMemberships) {
}
