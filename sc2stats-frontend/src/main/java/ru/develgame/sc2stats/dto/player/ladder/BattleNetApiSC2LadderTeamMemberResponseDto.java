package ru.develgame.sc2stats.dto.player.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiSC2LadderTeamMemberResponseDto(@JsonProperty("id") String id) {
}
