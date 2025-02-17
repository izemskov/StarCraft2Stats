package ru.develgame.sc2stats.backend.integration.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Import(TestDBConfiguration.class)
@TestPropertySource(locations = "/application-test.yml")
@ActiveProfiles("test")
public class BaseRepositoryIT {
}
