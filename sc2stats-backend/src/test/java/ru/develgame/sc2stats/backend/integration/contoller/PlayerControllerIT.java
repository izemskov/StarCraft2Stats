package ru.develgame.sc2stats.backend.integration.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.develgame.sc2stats.backend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRestControllerIT;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerIT extends BaseRestControllerIT {
    @Autowired
    private PlayerRepository playerRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void cleanUp() {
        playerRepository.deleteAll();
    }

    @Test
    void should_fetchOne() throws Exception {
        Player expected = Player.builder()
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
        expected = playerRepository.save(expected);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/player")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        PlayerResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                PlayerResponseDto.class);

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
