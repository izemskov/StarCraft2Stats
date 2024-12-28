package ru.develgame.sc2stats.backend.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRepositoryIT;
import ru.develgame.sc2stats.backend.repository.DailyRepository;

import java.util.List;

class DailyRepositoryIT extends BaseRepositoryIT {
    @Autowired
    private DailyRepository dailyRepository;

    @AfterEach
    void cleanUp() {
        dailyRepository.deleteAll();
    }

    @Test
    void should_findAllByOrderByTimestampDesc() {
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

        List<Daily> actual = dailyRepository.findAllByOrderByTimestampDesc();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(daily2.getId(), actual.get(0).getId());
        Assertions.assertEquals(daily1.getId(), actual.get(1).getId());
        Assertions.assertEquals(daily.getId(), actual.get(2).getId());
    }

    @Test
    void should_findByDateAndType() {
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
                .type("type1")
                .wins(5)
                .losses(10)
                .timestamp(2000000L)
                .date("02.01.2000")
                .build();
        daily2 = dailyRepository.save(daily2);

        Daily actual = dailyRepository.findByDateAndType("02.01.2000", "type");
        Assertions.assertEquals(actual.getId(), daily1.getId());
    }
}
