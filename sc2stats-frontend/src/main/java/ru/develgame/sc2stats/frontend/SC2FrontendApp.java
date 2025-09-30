package ru.develgame.sc2stats.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SC2FrontendApp {
    public static void main(String[] args) {
        SpringApplication.run(SC2FrontendApp.class, args);
    }
}
