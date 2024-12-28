package ru.develgame.sc2stats.backend.unit.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.develgame.sc2stats.backend.dto.DailyResponseDto;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.mapper.DailyMapper;

@ExtendWith(SpringExtension.class)
class DailyMapperTest {
    @InjectMocks
    private DailyMapper dailyMapper;

    @Test
    void should_mapToDto() {
        Daily expected = Daily.builder()
                .id(100L)
                .type("type")
                .wins(5)
                .losses(10)
                .timestamp(1000000L)
                .date("01.01.2000")
                .build();
        DailyResponseDto actual = dailyMapper.toDto(expected);

        Assertions.assertEquals(expected.getType(), actual.type());
        Assertions.assertEquals(expected.getWins(), actual.wins());
        Assertions.assertEquals(expected.getLosses(), actual.losses());
        Assertions.assertEquals(expected.getDate(), actual.date());
    }
}
