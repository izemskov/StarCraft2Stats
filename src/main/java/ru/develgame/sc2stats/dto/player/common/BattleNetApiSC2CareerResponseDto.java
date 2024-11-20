package ru.develgame.sc2stats.dto.player.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiSC2CareerResponseDto(@JsonProperty("totalCareerGames") int totalCareerGames,
                                               @JsonProperty("best1v1Finish") BattleNetApiSC2BestFinishResponseDto best1v1Finish,
                                               @JsonProperty("bestTeamFinish") BattleNetApiSC2BestFinishResponseDto bestTeamFinish) {
}
