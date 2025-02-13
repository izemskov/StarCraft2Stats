package ru.develgame.sc2stats.battlenet.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BattleNetAuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
