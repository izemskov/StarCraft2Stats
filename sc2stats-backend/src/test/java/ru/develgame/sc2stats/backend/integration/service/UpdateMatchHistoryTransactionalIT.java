package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.integration.configuration.TestExternalServiceConfiguration;
import ru.develgame.sc2stats.backend.repository.DailyRepository;
import ru.develgame.sc2stats.backend.repository.MapRepository;
import ru.develgame.sc2stats.backend.repository.MatchRepository;
import ru.develgame.sc2stats.backend.service.DailyService;
import ru.develgame.sc2stats.backend.service.battlenet.UpdateMatchHistoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class UpdateMatchHistoryTransactionalIT extends BaseServiceIT {
    @Autowired
    private UpdateMatchHistoryService updateMatchHistoryService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @MockBean
    private DailyService dailyService;

    @Autowired
    private MapRepository mapRepository;

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
        mapRepository.deleteAll();

        mockServer.stop();
    }

    @Test
    void should_notSaveMatchesIfTransactionRollback() {
        doThrow(new RuntimeException()).when(dailyService).updateDaily(any());

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

        Assertions.assertThrowsExactly(RuntimeException.class,
                () -> updateMatchHistoryService.updateMatchHistory("AccessTokenValue"));

        Assertions.assertEquals(0, matchRepository.findAll().size());
        Assertions.assertEquals(0, dailyRepository.findAll().size());
    }
}
