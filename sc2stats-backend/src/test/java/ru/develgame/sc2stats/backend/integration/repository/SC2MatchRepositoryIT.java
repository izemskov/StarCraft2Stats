package ru.develgame.sc2stats.backend.integration.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.SC2Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRepositoryIT;
import ru.develgame.sc2stats.backend.repository.SC2MatchRepository;

import java.util.List;

class SC2MatchRepositoryIT extends BaseRepositoryIT {
    @Autowired
    private SC2MatchRepository sc2MatchRepository;

    @Test
    void cleanUp() {
        sc2MatchRepository.deleteAll();
    }

    @Test
    void should_findAllByOrderByDateDesc() {
        SC2Match sc2Match = new SC2Match();
        sc2Match.setDate(1000000L);
        sc2Match = sc2MatchRepository.save(sc2Match);

        SC2Match sc2Match2 = new SC2Match();
        sc2Match2.setDate(2000000L);
        sc2Match2 = sc2MatchRepository.save(sc2Match2);

        SC2Match sc2Match3 = new SC2Match();
        sc2Match3.setDate(3000000L);
        sc2Match3 = sc2MatchRepository.save(sc2Match3);

        List<SC2Match> actual = sc2MatchRepository.findAllByOrderByDateDesc();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(sc2Match3.getId(), actual.get(0).getId());
        Assertions.assertEquals(sc2Match2.getId(), actual.get(1).getId());
        Assertions.assertEquals(sc2Match.getId(), actual.get(2).getId());

    }
}
