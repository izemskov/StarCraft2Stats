package ru.develgame.sc2stats.battlenet.oauth.configuration.battlenet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BattleNetPropertiesProxy {
    private final BattleNetProperties battleNetProperties;

    public String getClientId() {
        return battleNetProperties.clientId();
    }

    public String getClientSecret() {
        return battleNetProperties.clientSecret();
    }
}
