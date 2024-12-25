package ru.develgame.sc2stats.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SC2BackendApp {
    public static void main(String[] args) {
        SpringApplication.run(SC2BackendApp.class, args);
    }
}
