package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Map;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.repository.MapRepository;
import ru.develgame.sc2stats.backend.service.MapService;

import java.util.List;

import static ru.develgame.sc2stats.backend.utils.BattleNetConst.BATTLE_NET_MATCH_DECISION_LOSS;
import static ru.develgame.sc2stats.backend.utils.BattleNetConst.BATTLE_NET_MATCH_DECISION_WIN;

class MapServiceIT extends BaseServiceIT {
    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapService mapService;

    @AfterEach
    void cleanUp() {
        mapRepository.deleteAll();
    }

    @Test
    void should_updateMap() {
        Match match = new Match();
        match.setMap("map");
        match.setDate(1000000L);
        match.setType("type");
        match.setDecision(BATTLE_NET_MATCH_DECISION_WIN);

        mapService.updateMap(match);

        Assertions.assertEquals(1, mapRepository.findAll().size());
        Map actual = mapRepository.findAll().get(0);
        Assertions.assertEquals("type", actual.getType());
        Assertions.assertEquals("map", actual.getName());
        Assertions.assertEquals(1, actual.getWins());
        Assertions.assertEquals(0, actual.getLosses());

        Match match2 = new Match();
        match2.setMap("map");
        match2.setType("type");
        match2.setDecision(BATTLE_NET_MATCH_DECISION_LOSS);

        mapService.updateMap(match2);

        Assertions.assertEquals(1, mapRepository.findAll().size());
        actual = mapRepository.findAll().get(0);
        Assertions.assertEquals("type", actual.getType());
        Assertions.assertEquals("map", actual.getName());
        Assertions.assertEquals(1, actual.getWins());
        Assertions.assertEquals(1, actual.getLosses());

        Match match3 = new Match();
        match3.setMap("map");
        match3.setType("type1");
        match3.setDecision(BATTLE_NET_MATCH_DECISION_WIN);

        mapService.updateMap(match3);

        Assertions.assertEquals(2, mapRepository.findAll().size());
        actual = mapRepository.findAll().get(1);
        Assertions.assertEquals("type1", actual.getType());
        Assertions.assertEquals("map", actual.getName());
        Assertions.assertEquals(1, actual.getWins());
        Assertions.assertEquals(0, actual.getLosses());
    }

    @Test
    void should_fetchAllByType() {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .actual(false)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        List<Map> actual = mapService.fetchAll(MatchType.TYPE_1X1, null);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(map.getId(), actual.get(0).getId());
        Assertions.assertEquals(map2.getId(), actual.get(1).getId());
    }

    @Test
    void should_fetchAllByActual() {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .actual(false)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        List<Map> actual = mapService.fetchAll(null, true);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(map.getId(), actual.get(0).getId());
        Assertions.assertEquals(map1.getId(), actual.get(1).getId());
    }

    @Test
    void should_fetchAllByActualAndType() {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .actual(false)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        List<Map> actual = mapService.fetchAll(MatchType.TYPE_1X1, true);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(map.getId(), actual.get(0).getId());
    }
}
