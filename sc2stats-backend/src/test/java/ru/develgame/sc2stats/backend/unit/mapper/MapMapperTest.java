package ru.develgame.sc2stats.backend.unit.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.develgame.sc2stats.backend.dto.MapResponseDto;
import ru.develgame.sc2stats.backend.entity.Map;
import ru.develgame.sc2stats.backend.mapper.MapMapper;

@ExtendWith(SpringExtension.class)
class MapMapperTest {

    @InjectMocks
    private MapMapper mapMapper;

    @Test
    void should_mapToDto() {
        Map expected = Map.builder()
                .id(100L)
                .name("name")
                .type("type")
                .wins(5)
                .losses(10)
                .build();
        MapResponseDto actual = mapMapper.toDto(expected);

        Assertions.assertEquals(expected.getType(), actual.type());
        Assertions.assertEquals(expected.getWins(), actual.wins());
        Assertions.assertEquals(expected.getLosses(), actual.losses());
        Assertions.assertEquals(expected.getName(), actual.name());
    }
}
