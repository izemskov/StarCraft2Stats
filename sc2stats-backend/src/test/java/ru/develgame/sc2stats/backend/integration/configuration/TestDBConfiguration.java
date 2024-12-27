package ru.develgame.sc2stats.backend.integration.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EntityScan("ru.develgame.sc2stats.backend.entity")
@EnableJpaRepositories("ru.develgame.sc2stats.backend.repository")
public class TestDBConfiguration {
}
