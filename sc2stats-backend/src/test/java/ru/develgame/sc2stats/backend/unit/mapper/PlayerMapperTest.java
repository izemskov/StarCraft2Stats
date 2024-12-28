package ru.develgame.sc2stats.backend.unit.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.develgame.sc2stats.backend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.mapper.PlayerMapper;

@ExtendWith(SpringExtension.class)
class PlayerMapperTest {
    @InjectMocks
    private PlayerMapper playerMapper;

    @Test
    void should_mapToDto() {
        Player expected = Player.builder()
                .id(100L)
                .totalCareerGames(2)
                .best1v1FinishLeagueName("league1")
                .best1v1FinishTimesAchieved(3)
                .bestTeamFinishLeagueName("league2")
                .bestTeamFinishTimesAchieved(4)
                .currentMMR(5)
                .bestMMR(6)
                .currentMMR2x2(7)
                .bestMMR2x2(8)
                .build();
        PlayerResponseDto actual = playerMapper.toDto(expected);

        Assertions.assertEquals(expected.getTotalCareerGames(), actual.totalCareerGames());
        Assertions.assertEquals(expected.getBest1v1FinishLeagueName(), actual.best1v1FinishLeagueName());
        Assertions.assertEquals(expected.getBest1v1FinishTimesAchieved(), actual.best1v1FinishTimesAchieved());
        Assertions.assertEquals(expected.getBestTeamFinishLeagueName(), actual.bestTeamFinishLeagueName());
        Assertions.assertEquals(expected.getBestTeamFinishTimesAchieved(), actual.bestTeamFinishTimesAchieved());
        Assertions.assertEquals(expected.getCurrentMMR(), actual.currentMMR());
        Assertions.assertEquals(expected.getBestMMR(), actual.bestMMR());
        Assertions.assertEquals(expected.getCurrentMMR2x2(), actual.currentMMR2x2());
        Assertions.assertEquals(expected.getBestMMR2x2(), actual.bestMMR2x2());
    }
}
