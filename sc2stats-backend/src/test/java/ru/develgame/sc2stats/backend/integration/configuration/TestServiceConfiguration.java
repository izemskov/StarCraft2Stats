package ru.develgame.sc2stats.backend.integration.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("ru.develgame.sc2stats.backend.service")
public class TestServiceConfiguration {
}
