package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.integration.configuration.TestExternalServiceConfiguration;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;
import ru.develgame.sc2stats.backend.service.battlenet.UpdatePlayerInfoService;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class UpdatePlayerInfoServiceIT extends BaseServiceIT {
    @Autowired
    private UpdatePlayerInfoService updatePlayerInfoService;

    @Autowired
    private PlayerRepository playerRepository;

    private ClientAndServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = TestExternalServiceConfiguration.getStartedServer();

        ReflectionTestUtils.setField(updatePlayerInfoService, "baseUrl",
                "http://localhost:" + mockServer.getLocalPort());
    }

    @AfterEach
    public void cleanUp() {
        playerRepository.deleteAll();

        mockServer.stop();
    }

    @Test
    void should_updatePlayerInfo() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/profile/2/2/playerId")
                        .withQueryStringParameter("locale", "en_US")
                        .withHeader("Authorization", "Bearer AccessTokenValue")
                )
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody("""
                                {
                                     "summary": {
                                         "id": "playerId",
                                         "realm": 2,
                                         "displayName": "playerName",
                                         "clanName": "clanName",
                                         "clanTag": "clanTag",
                                         "totalSwarmLevel": 150,
                                         "totalAchievementPoints": 2460
                                     },
                                     "career": {
                                         "terranWins": 0,
                                         "zergWins": 28,
                                         "protossWins": 14,
                                         "totalCareerGames": 2165,
                                         "totalGamesThisSeason": 77,
                                         "current1v1LeagueName": "PLATINUM",
                                         "currentBestTeamLeagueName": "DIAMOND",
                                         "best1v1Finish": {
                                             "leagueName": "DIAMOND",
                                             "timesAchieved": 2
                                         },
                                         "bestTeamFinish": {
                                             "leagueName": "MASTER",
                                             "timesAchieved": 1
                                         }
                                     }
                                }
                                """)
                        .withStatusCode(200));

        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/profile/2/2/playerId/ladder/summary")
                        .withQueryStringParameter("locale", "en_US")
                        .withHeader("Authorization", "Bearer AccessTokenValue")
                )
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody("""
                                {
                                     "allLadderMemberships": [
                                          {
                                              "ladderId": "262035",
                                              "localizedGameMode": "1v1 Platinum",
                                              "rank": 32
                                          },
                                          {
                                              "ladderId": "262241",
                                              "localizedGameMode": "2v2 Diamond",
                                              "rank": 42
                                          }
                                     ]
                                }
                                """)
                        .withStatusCode(200));

        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/profile/2/2/playerId/ladder/262035")
                        .withQueryStringParameter("locale", "en_US")
                        .withHeader("Authorization", "Bearer AccessTokenValue")
                )
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody("""
                                {
                                     "ladderTeams": [
                                         {
                                             "teamMembers": [
                                                 {
                                                     "id": "playerId",
                                                     "realm": 2,
                                                     "region": 2,
                                                     "displayName": "playerName",
                                                     "clanTag": "clanTag",
                                                     "favoriteRace": "zerg"
                                                 }
                                             ],
                                             "previousRank": 12,
                                             "points": 548,
                                             "wins": 25,
                                             "losses": 19,
                                             "mmr": 3092,
                                             "joinTimestamp": 1732026185
                                         },
                                         {
                                             "teamMembers": [
                                                 {
                                                     "id": "playerId2",
                                                     "realm": 1,
                                                     "region": 2,
                                                     "displayName": "playerName2",
                                                     "favoriteRace": "zerg"
                                                 }
                                             ],
                                             "previousRank": 40,
                                             "points": 547,
                                             "wins": 27,
                                             "losses": 16,
                                             "mmr": 3045,
                                             "joinTimestamp": 1732047554
                                         }
                                     ]
                                }
                                """)
                        .withStatusCode(200));

        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/profile/2/2/playerId/ladder/262241")
                        .withQueryStringParameter("locale", "en_US")
                        .withHeader("Authorization", "Bearer AccessTokenValue")
                )
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody("""
                                {
                                     "ladderTeams": [
                                         {
                                             "teamMembers": [
                                                 {
                                                     "id": "playerId",
                                                     "realm": 2,
                                                     "region": 2,
                                                     "displayName": "playerName",
                                                     "clanTag": "clanTag",
                                                     "favoriteRace": "zerg"
                                                 }
                                             ],
                                             "previousRank": 12,
                                             "points": 548,
                                             "wins": 25,
                                             "losses": 19,
                                             "mmr": 3084,
                                             "joinTimestamp": 1732026185
                                         },
                                         {
                                             "teamMembers": [
                                                 {
                                                     "id": "playerId2",
                                                     "realm": 1,
                                                     "region": 2,
                                                     "displayName": "playerName2",
                                                     "favoriteRace": "zerg"
                                                 }
                                             ],
                                             "previousRank": 40,
                                             "points": 547,
                                             "wins": 27,
                                             "losses": 16,
                                             "mmr": 3045,
                                             "joinTimestamp": 1732047554
                                         }
                                     ]
                                }
                                """)
                        .withStatusCode(200));

        updatePlayerInfoService.updatePlayerInfo("AccessTokenValue");

        Assertions.assertEquals(1, playerRepository.findAll().size());
        Player actual = playerRepository.findAll().get(0);
        Assertions.assertEquals(2165, actual.getTotalCareerGames());
        Assertions.assertEquals("DIAMOND", actual.getBest1v1FinishLeagueName());
        Assertions.assertEquals(2, actual.getBest1v1FinishTimesAchieved());
        Assertions.assertEquals("MASTER", actual.getBestTeamFinishLeagueName());
        Assertions.assertEquals(1, actual.getBestTeamFinishTimesAchieved());
        Assertions.assertEquals(3092, actual.getCurrentMMR());
        Assertions.assertEquals(3084, actual.getCurrentMMR2x2());
    }
}
