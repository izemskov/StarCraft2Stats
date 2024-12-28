package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.repository.DailyRepository;
import ru.develgame.sc2stats.backend.service.DailyService;

import java.util.List;

import static ru.develgame.sc2stats.backend.utils.BattleNetConst.BATTLE_NET_MATCH_DECISION_LOSS;
import static ru.develgame.sc2stats.backend.utils.BattleNetConst.BATTLE_NET_MATCH_DECISION_WIN;

class DailyServiceIT extends BaseServiceIT {
    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private DailyService dailyService;

    @AfterEach
    void cleanUp() {
        dailyRepository.deleteAll();
    }

    @Test
    void should_fetchAllSortedByDateDesc() {
        Daily daily = Daily.builder()
                .type("type")
                .wins(5)
                .losses(10)
                .timestamp(1000000L)
                .date("01.01.2000")
                .build();
        daily = dailyRepository.save(daily);

        Daily daily1 = Daily.builder()
                .type("type")
                .wins(5)
                .losses(10)
                .timestamp(2000000L)
                .date("02.01.2000")
                .build();
        daily1 = dailyRepository.save(daily1);

        Daily daily2 = Daily.builder()
                .type("type")
                .wins(5)
                .losses(10)
                .timestamp(3000000L)
                .date("03.01.2000")
                .build();
        daily2 = dailyRepository.save(daily2);

        List<Daily> actual = dailyService.fetchAllSortedByDateDesc();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(daily2.getId(), actual.get(0).getId());
        Assertions.assertEquals(daily1.getId(), actual.get(1).getId());
        Assertions.assertEquals(daily.getId(), actual.get(2).getId());
    }

    @Test
    void should_updateDaily() {
        Match match = new Match();
        match.setDate(1000000L);
        match.setType("type");
        match.setDecision(BATTLE_NET_MATCH_DECISION_WIN);

        dailyService.updateDaily(match);

        Assertions.assertEquals(1, dailyRepository.findAll().size());
        Daily actual = dailyRepository.findAll().get(0);
        Assertions.assertEquals("type", actual.getType());
        Assertions.assertEquals(1, actual.getWins());
        Assertions.assertEquals(0, actual.getLosses());

        Match match2 = new Match();
        match2.setDate(1000000L);
        match2.setType("type");
        match2.setDecision(BATTLE_NET_MATCH_DECISION_LOSS);

        dailyService.updateDaily(match2);

        Assertions.assertEquals(1, dailyRepository.findAll().size());
        actual = dailyRepository.findAll().get(0);
        Assertions.assertEquals("type", actual.getType());
        Assertions.assertEquals(1, actual.getWins());
        Assertions.assertEquals(1, actual.getLosses());

        Match match3 = new Match();
        match3.setDate(1000000L);
        match3.setType("type1");
        match3.setDecision(BATTLE_NET_MATCH_DECISION_WIN);

        dailyService.updateDaily(match3);

        Assertions.assertEquals(2, dailyRepository.findAll().size());
        actual = dailyRepository.findAll().get(1);
        Assertions.assertEquals("type1", actual.getType());
        Assertions.assertEquals(1, actual.getWins());
        Assertions.assertEquals(0, actual.getLosses());
    }
}
