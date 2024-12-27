package ru.develgame.sc2stats.backend.integration.configuration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({TestDBConfiguration.class,
        TestServiceConfiguration.class})
@TestPropertySource(locations = "/application-test.yml")
@ActiveProfiles("test")
public class BaseServiceIT {
}
