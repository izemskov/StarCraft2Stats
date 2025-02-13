package ru.develgame.sc2stats.battlenet.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record BattleNetUserInfoDto(@JsonProperty("sub") String sub,
                                   @JsonProperty("id") String id,
                                   @JsonProperty("battletag") String battletag) {
}
