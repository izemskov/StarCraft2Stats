package ru.develgame.sc2stats.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class SC2FrontendApp {
    public static void main(String[] args) {
        SpringApplication.run(SC2FrontendApp.class, args);
    }
}
