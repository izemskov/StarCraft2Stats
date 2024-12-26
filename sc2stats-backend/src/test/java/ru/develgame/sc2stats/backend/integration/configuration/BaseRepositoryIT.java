package ru.develgame.sc2stats.backend.integration.configuration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EntityScan("ru.develgame.sc2stats.backend.entity")
@EnableJpaRepositories("ru.develgame.sc2stats.backend.repository")
@TestPropertySource(locations = "/application-test.yml")
@ActiveProfiles("test")
public class BaseRepositoryIT {
}
