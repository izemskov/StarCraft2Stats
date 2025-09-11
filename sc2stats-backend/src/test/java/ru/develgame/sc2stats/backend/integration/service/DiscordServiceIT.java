package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.integration.configuration.TestExternalServiceConfiguration;
import ru.develgame.sc2stats.backend.repository.DailyRepository;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;
import ru.develgame.sc2stats.backend.service.discord.DiscordScheduler;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

class DiscordServiceIT extends BaseServiceIT {
    @Autowired
    private DiscordScheduler discordScheduler;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private ClientAndServer mockServer;

    @BeforeEach
    void init() {
        mockServer = TestExternalServiceConfiguration.getStartedServer();

        ReflectionTestUtils.setField(discordScheduler, "webhook",
                "http://localhost:" + mockServer.getLocalPort() + "/send");
    }

    @AfterEach
    void cleanUp() {
        playerRepository.deleteAll();
        dailyRepository.deleteAll();
    }

    @Test
    void should_sendDiscordMessage() {
        Player player = new Player();
        player.setCurrentMMR2x2(1000);
        playerRepository.save(player);

        Daily daily = new Daily();
        daily.setDate("01-01-2020");
        daily.setLosses(5);
        daily.setWins(6);
        daily.setType("2v2");
        dailyRepository.save(daily);

        Daily daily1 = new Daily();
        daily1.setDate("02-01-2020");
        daily1.setLosses(4);
        daily1.setWins(3);
        daily1.setType("1v1");
        daily1.setSend(true);
        dailyRepository.save(daily1);

        mockServer.when(request()
                        .withMethod("POST")
                        .withPath("/send")
                        .withBody(json("""
                                {
                                "content": "###################
                                Date: 01-01-2020
                                Type: 2v2
                                Result: 6-5
                                MMR: 1000
                                "
                                }
                                """))
                )
                .respond(response()
                        .withStatusCode(200));

        discordScheduler.sendDailyStats();
    }
}
