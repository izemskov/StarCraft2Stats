package ru.develgame.sc2stats.backend.dto.battlenet.player.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiCareerResponseDto(@JsonProperty("totalCareerGames") int totalCareerGames,
                                            @JsonProperty("best1v1Finish") BattleNetApiBestFinishResponseDto best1v1Finish,
                                            @JsonProperty("bestTeamFinish") BattleNetApiBestFinishResponseDto bestTeamFinish) {
}
