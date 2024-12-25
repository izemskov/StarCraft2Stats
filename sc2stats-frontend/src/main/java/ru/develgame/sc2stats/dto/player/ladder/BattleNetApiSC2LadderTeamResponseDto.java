package ru.develgame.sc2stats.dto.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BattleNetApiSC2LadderTeamResponseDto(@JsonProperty("mmr") int mmr,
                                                   @JsonProperty("teamMembers") List<BattleNetApiSC2LadderTeamMemberResponseDto> teamMembers) {
}
