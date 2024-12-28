package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;
import ru.develgame.sc2stats.backend.service.PlayerService;

class PlayerServiceIT extends BaseServiceIT {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @AfterEach
    void cleanUp() {
        playerRepository.deleteAll();
    }

    @Test
    void should_getCurrentPlayer() {
        Player expected = Player.builder()
                .totalCareerGames(2)
                .best1v1FinishLeagueName("league1")
                .best1v1FinishTimesAchieved(3)
                .bestTeamFinishLeagueName("league2")
                .bestTeamFinishTimesAchieved(4)
                .currentMMR(5)
                .bestMMR(6)
                .currentMMR2x2(7)
                .bestMMR2x2(8)
                .build();
        expected = playerRepository.save(expected);

        Player actual = playerService.getCurrentPlayer();

        Assertions.assertEquals(expected.getId(), actual.getId());
    }
}
