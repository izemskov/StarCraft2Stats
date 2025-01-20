package ru.develgame.sc2stats.backend.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRepositoryIT;
import ru.develgame.sc2stats.backend.repository.MatchRepository;

import java.util.List;

class MatchRepositoryIT extends BaseRepositoryIT {
    @Autowired
    private MatchRepository matchRepository;

    @AfterEach
    void cleanUp() {
        matchRepository.deleteAll();
    }

    @Test
    void should_findAllByOrderByDateDesc() {
        Match match = new Match();
        match.setDate(1000000L);
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        List<Match> actual = matchRepository.findAllByOrderByDateDesc();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(match3.getId(), actual.get(0).getId());
        Assertions.assertEquals(match2.getId(), actual.get(1).getId());
        Assertions.assertEquals(match.getId(), actual.get(2).getId());
    }

    @Test
    void should_findByDate() {
        Match match = new Match();
        match.setDate(1000000L);
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        Match actual = matchRepository.findByDate(2000000L);
        Assertions.assertEquals(match2.getId(), actual.getId());
    }
}
