package ru.develgame.sc2stats.backend.integration.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.backend.configuration.battlenetapi.BattleNetProperties;

@TestConfiguration
@ComponentScan("ru.develgame.sc2stats.backend.service")
@EnableConfigurationProperties(value = {BattleNetProperties.class})
public class TestServiceConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
