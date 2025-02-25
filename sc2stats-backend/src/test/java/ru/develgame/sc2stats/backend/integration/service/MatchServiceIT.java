package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
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
        match.setType("1v1");
        match.setDecision("Win");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        List<Match> actual = matchService.fetchAllMatchesSortedByDateDesc(null, null);
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(match3.getId(), actual.get(0).getId());
        Assertions.assertEquals(match2.getId(), actual.get(1).getId());
        Assertions.assertEquals(match.getId(), actual.get(2).getId());
    }

    @Test
    void should_fetchAll_whenMatcheTypeNoneAndDecisionNone() {
        Match match = new Match();
        match.setDate(1000000L);
        match.setType("1v1");
        match.setDecision("Win");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        List<Match> actual = matchService.fetchAllMatchesSortedByDateDesc(MatchType.TYPE_NONE, MatchDecision.NONE);
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(match3.getId(), actual.get(0).getId());
        Assertions.assertEquals(match2.getId(), actual.get(1).getId());
        Assertions.assertEquals(match.getId(), actual.get(2).getId());
    }

    @Test
    void should_fetchAll_whenMatchesByType() {
        Match match = new Match();
        match.setDate(1000000L);
        match.setType("2v2");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2.setType("3v3");
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3.setType("1v1");
        match3 = matchRepository.save(match3);

        Match match4 = new Match();
        match4.setDate(4000000L);
        match4.setType("1v1");
        match4 = matchRepository.save(match4);

        Match match5 = new Match();
        match5.setDate(5000000L);
        match5 = matchRepository.save(match5);

        List<Match> actual = matchService.fetchAllMatchesSortedByDateDesc(MatchType.TYPE_1X1, null);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(match4.getId(), actual.get(0).getId());
        Assertions.assertEquals(match3.getId(), actual.get(1).getId());
    }

    @Test
    void should_fetchAll_whenMatchesByDecision() {
        Match match = new Match();
        match.setDate(1000000L);
        match.setDecision("Loss");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2.setDecision("Loss");
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3.setDecision("Win");
        match3 = matchRepository.save(match3);

        Match match4 = new Match();
        match4.setDate(4000000L);
        match4.setDecision("Win");
        match4 = matchRepository.save(match4);

        Match match5 = new Match();
        match5.setDate(5000000L);
        match5 = matchRepository.save(match5);

        List<Match> actual = matchService.fetchAllMatchesSortedByDateDesc(null, MatchDecision.WIN);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(match4.getId(), actual.get(0).getId());
        Assertions.assertEquals(match3.getId(), actual.get(1).getId());
    }

    @Test
    void should_fetchAll_whenMatchesByTypeAndDecision() {
        Match match = new Match();
        match.setDate(1000000L);
        match.setType("2v2");
        match.setDecision("Loss");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2.setType("1v1");
        match2.setDecision("Loss");
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3.setType("1v1");
        match3.setDecision("Win");
        match3 = matchRepository.save(match3);

        Match match4 = new Match();
        match4.setDate(4000000L);
        match4.setType("2v2");
        match4.setDecision("Win");
        match4 = matchRepository.save(match4);

        Match match5 = new Match();
        match5.setDate(5000000L);
        match5 = matchRepository.save(match5);

        List<Match> actual = matchService.fetchAllMatchesSortedByDateDesc(MatchType.TYPE_1X1, MatchDecision.WIN);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(match3.getId(), actual.get(0).getId());
    }
}
