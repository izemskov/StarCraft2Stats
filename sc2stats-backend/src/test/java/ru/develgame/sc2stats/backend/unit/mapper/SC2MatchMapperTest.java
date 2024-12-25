package ru.develgame.sc2stats.backend.unit.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.develgame.sc2stats.backend.dto.SC2MatchResponseDto;
import ru.develgame.sc2stats.backend.entity.SC2Match;
import ru.develgame.sc2stats.backend.mapper.SC2MatchMapper;

@ExtendWith(SpringExtension.class)
class SC2MatchMapperTest {

    @InjectMocks
    private SC2MatchMapper sc2MatchMapper;

    @Test
    void should_mapToDto() {
        SC2Match expected = new SC2Match(100L, "map", "type", "decision", "speed", 1000000L);
        SC2MatchResponseDto actual = sc2MatchMapper.toDto(expected);

        Assertions.assertEquals(expected.getMap(), actual.map());
        Assertions.assertEquals(expected.getType(), actual.type());
        Assertions.assertEquals(expected.getDecision(), actual.decision());
        Assertions.assertEquals(expected.getDate(), actual.date());
    }
}
