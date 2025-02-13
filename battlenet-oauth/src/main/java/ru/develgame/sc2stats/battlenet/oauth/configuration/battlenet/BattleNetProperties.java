package ru.develgame.sc2stats.battlenet.oauth.configuration.battlenet;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "battlenet")
public record BattleNetProperties(@NotNull String clientId,
                                  @NotNull String clientSecret) {
}
