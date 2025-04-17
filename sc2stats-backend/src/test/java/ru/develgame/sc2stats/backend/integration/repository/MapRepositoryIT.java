package ru.develgame.sc2stats.backend.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.Map;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRepositoryIT;
import ru.develgame.sc2stats.backend.repository.MapRepository;

import java.util.List;

class MapRepositoryIT extends BaseRepositoryIT {
    @Autowired
    private MapRepository mapRepository;

    @AfterEach
    void cleanUp() {
        mapRepository.deleteAll();
    }

    @Test
    void should_findByNameAndType() {
        Map map = Map.builder()
                .name("name")
                .type("type")
                .wins(5)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("type1")
                .wins(5)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("type")
                .wins(5)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("type1")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);


        Map actual = mapRepository.findByNameAndType("name", "type");
        Assertions.assertEquals(map.getId(), actual.getId());
    }

    @Test
    void should_findAllByType() {
        Map map = Map.builder()
                .name("name")
                .type("type")
                .wins(5)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("type1")
                .wins(5)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("type")
                .wins(5)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("type1")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);


        List<Map> actual = mapRepository.findAllByType("type");
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(map.getId(), actual.get(0).getId());
        Assertions.assertEquals(map2.getId(), actual.get(1).getId());
    }
}
