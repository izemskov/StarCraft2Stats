package ru.develgame.sc2stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BattleNetApiAccessTokenResponseDto(@JsonProperty("access_token") String accessToken) {
}
