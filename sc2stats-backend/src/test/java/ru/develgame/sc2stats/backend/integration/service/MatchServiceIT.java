package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.repository.MatchRepository;
import ru.develgame.sc2stats.backend.service.MatchService;

import java.util.List;

class MatchServiceIT extends BaseServiceIT {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchService matchService;

    @AfterEach
    void cleanUp() {
        matchRepository.deleteAll();
    }

    @Test
    void should_fetchAllMatchesSortedByDateDesc() {
        Match match = new Match();
        match.setDate(1000000L);
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        List<Match> actual = matchService.fetchAllMatchesSortedByDateDesc();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(match3.getId(), actual.get(0).getId());
        Assertions.assertEquals(match2.getId(), actual.get(1).getId());
        Assertions.assertEquals(match.getId(), actual.get(2).getId());
    }
}
