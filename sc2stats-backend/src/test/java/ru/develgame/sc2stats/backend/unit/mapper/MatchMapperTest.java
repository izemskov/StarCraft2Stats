package ru.develgame.sc2stats.backend.unit.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.develgame.sc2stats.backend.dto.MatchResponseDto;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.mapper.MatchMapper;

@ExtendWith(SpringExtension.class)
class MatchMapperTest {

    @InjectMocks
    private MatchMapper matchMapper;

    @Test
    void should_mapToDto() {
        Match expected = new Match(100L, "map", "type", "decision", "speed", 1000000L);
        MatchResponseDto actual = matchMapper.toDto(expected);

        Assertions.assertEquals(expected.getMap(), actual.map());
        Assertions.assertEquals(expected.getType(), actual.type());
        Assertions.assertEquals(expected.getDecision(), actual.decision());
        Assertions.assertEquals(expected.getDate(), actual.date());
    }
}
