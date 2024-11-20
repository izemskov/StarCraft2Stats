package ru.develgame.sc2stats.dto.player.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiSC2AllPlayerInfoResponseDto(@JsonProperty("career") BattleNetApiSC2CareerResponseDto career) {
}
