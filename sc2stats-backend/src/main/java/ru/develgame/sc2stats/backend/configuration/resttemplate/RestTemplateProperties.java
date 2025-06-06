package ru.develgame.sc2stats.backend.configuration.resttemplate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "sc.rest-template")
public record RestTemplateProperties(int connectTimeout, int responseTimeout) {
}
