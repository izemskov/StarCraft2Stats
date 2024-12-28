package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.integration.configuration.TestExternalServiceConfiguration;
import ru.develgame.sc2stats.backend.repository.DailyRepository;
import ru.develgame.sc2stats.backend.repository.MatchRepository;
import ru.develgame.sc2stats.backend.service.battlenet.UpdateMatchHistoryService;

import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class UpdateMatchHistoryServiceIT extends BaseServiceIT {
    @Autowired
    private UpdateMatchHistoryService updateMatchHistoryService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private DailyRepository dailyRepository;

    private ClientAndServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = TestExternalServiceConfiguration.getStartedServer();

        ReflectionTestUtils.setField(updateMatchHistoryService, "baseUrl",
                "http://localhost:" + mockServer.getLocalPort());
    }

    @AfterEach
    public void cleanUp() {
        matchRepository.deleteAll();
        dailyRepository.deleteAll();

        mockServer.stop();
    }

    @Test
    void should_updateMatchHistory() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/legacy/profile/2/2/playerId/matches")
                        .withHeader("Authorization", "Bearer AccessTokenValue")
                )
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody("""
                                {
                                     "matches": [
                                         {
                                             "map": "map1",
                                             "type": "1v1",
                                             "decision": "Win",
                                             "speed": "Faster",
                                             "date": 1733572914
                                         },
                                         {
                                             "map": "map2",
                                             "type": "1v1",
                                             "decision": "Loss",
                                             "speed": "Faster",
                                             "date": 1733571831
                                         },
                                         {
                                             "map": "map3",
                                             "type": "2v2",
                                             "decision": "Win",
                                             "speed": "Faster",
                                             "date": 1733571168
                                         }
                                     ]
                                }
                                """)
                        .withStatusCode(200));

        updateMatchHistoryService.updateMatchHistory("AccessTokenValue");

        Assertions.assertEquals(2, dailyRepository.findAll().size());
        List<Daily> actual = dailyRepository.findAll();
        Assertions.assertEquals("1v1", actual.get(0).getType());
        Assertions.assertEquals(1, actual.get(0).getWins());
        Assertions.assertEquals(1, actual.get(0).getLosses());
        Assertions.assertEquals("2v2", actual.get(1).getType());
        Assertions.assertEquals(1, actual.get(1).getWins());
        Assertions.assertEquals(0, actual.get(1).getLosses());

        Assertions.assertEquals(3, matchRepository.findAll().size());
        List<Match> actual1 = matchRepository.findAll();
        Assertions.assertEquals("map1", actual1.get(0).getMap());
        Assertions.assertEquals("map2", actual1.get(1).getMap());
        Assertions.assertEquals("map3", actual1.get(2).getMap());
    }
}
