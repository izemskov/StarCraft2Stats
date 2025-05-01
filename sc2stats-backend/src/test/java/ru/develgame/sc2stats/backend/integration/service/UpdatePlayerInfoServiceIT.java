package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.Expectation;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.develgame.sc2stats.backend.entity.MatchMakingRating;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.integration.configuration.TestExternalServiceConfiguration;
import ru.develgame.sc2stats.backend.repository.MatchMakingRatingRepository;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;
import ru.develgame.sc2stats.backend.service.battlenet.UpdatePlayerInfoService;

import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class UpdatePlayerInfoServiceIT extends BaseServiceIT {
    @Autowired
    private UpdatePlayerInfoService updatePlayerInfoService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchMakingRatingRepository matchMakingRatingRepository;

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
        matchMakingRatingRepository.deleteAll();

        mockServer.stop();
    }

    @Test
    void should_updatePlayerInfo() {
        setUpMockServer();
        setUpMockServer3v3();

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
        Assertions.assertEquals(2962, actual.getCurrentMMR3x3());

        List<MatchMakingRating> mmr = matchMakingRatingRepository.findAll();
        Assertions.assertEquals(3, mmr.size());
        Assertions.assertEquals(3092, mmr.get(0).getMmrValue());
        Assertions.assertEquals("1v1", mmr.get(0).getType());
        Assertions.assertEquals(3084, mmr.get(1).getMmrValue());
        Assertions.assertEquals("2v2", mmr.get(1).getType());
        Assertions.assertEquals(2962, mmr.get(2).getMmrValue());
        Assertions.assertEquals("3v3", mmr.get(2).getType());
    }

    @Test
    void should_updateOnlyOneMMR() {
        setUpMockServer();
        String expectationId = setUpMockServer3v3();

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
        Assertions.assertEquals(2962, actual.getCurrentMMR3x3());

        List<MatchMakingRating> mmr = matchMakingRatingRepository.findAll();
        Assertions.assertEquals(3, mmr.size());
        Assertions.assertEquals(3092, mmr.get(0).getMmrValue());
        Assertions.assertEquals("1v1", mmr.get(0).getType());
        Assertions.assertEquals(3084, mmr.get(1).getMmrValue());
        Assertions.assertEquals("2v2", mmr.get(1).getType());
        Assertions.assertEquals(2962, mmr.get(2).getMmrValue());
        Assertions.assertEquals("3v3", mmr.get(2).getType());

        mockServer.clear(expectationId);

        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/profile/2/2/playerId/ladder/262253")
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
                                             "mmr": 3005,
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
                                             "mmr": 1446,
                                             "joinTimestamp": 1732047554
                                         }
                                     ]
                                }
                                """)
                        .withStatusCode(200));

        updatePlayerInfoService.updatePlayerInfo("AccessTokenValue");

        Assertions.assertEquals(1, playerRepository.findAll().size());
        actual = playerRepository.findAll().get(0);
        Assertions.assertEquals(2165, actual.getTotalCareerGames());
        Assertions.assertEquals("DIAMOND", actual.getBest1v1FinishLeagueName());
        Assertions.assertEquals(2, actual.getBest1v1FinishTimesAchieved());
        Assertions.assertEquals("MASTER", actual.getBestTeamFinishLeagueName());
        Assertions.assertEquals(1, actual.getBestTeamFinishTimesAchieved());
        Assertions.assertEquals(3092, actual.getCurrentMMR());
        Assertions.assertEquals(3084, actual.getCurrentMMR2x2());
        Assertions.assertEquals(3005, actual.getCurrentMMR3x3());

        mmr = matchMakingRatingRepository.findAll();
        Assertions.assertEquals(4, mmr.size());
        Assertions.assertEquals(3092, mmr.get(0).getMmrValue());
        Assertions.assertEquals("1v1", mmr.get(0).getType());
        Assertions.assertEquals(3084, mmr.get(1).getMmrValue());
        Assertions.assertEquals("2v2", mmr.get(1).getType());
        Assertions.assertEquals(2962, mmr.get(2).getMmrValue());
        Assertions.assertEquals("3v3", mmr.get(2).getType());
        Assertions.assertEquals(3005, mmr.get(3).getMmrValue());
        Assertions.assertEquals("3v3", mmr.get(3).getType());
    }

    private void setUpMockServer() {
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
                                          },
                                          {
                                              "ladderId": "262253",
                                              "localizedGameMode": "3v3 Platinum",
                                              "rank": 17
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
    }

    private String setUpMockServer3v3() {
        Expectation[] expectations = mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/sc2/profile/2/2/playerId/ladder/262253")
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
                                             "mmr": 2962,
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
                                             "mmr": 1446,
                                             "joinTimestamp": 1732047554
                                         }
                                     ]
                                }
                                """)
                        .withStatusCode(200));
        return expectations[0].getId();
    }
}
