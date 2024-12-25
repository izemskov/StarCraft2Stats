package ru.develgame.sc2stats.dto.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BattleNetApiSC2PlayerLadderResponseDto(
        @JsonProperty("allLadderMemberships") List<BattleNetApiSC2PlayerLadderShowcaseResponseDto> allLadderMemberships) {
}
