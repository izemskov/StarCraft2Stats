package ru.develgame.sc2stats.backend.integration.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.develgame.sc2stats.backend.dto.DailyResponseDto;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRestControllerIT;
import ru.develgame.sc2stats.backend.repository.DailyRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DailyControllerIT extends BaseRestControllerIT {
    @Autowired
    private DailyRepository dailyRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void cleanUp() {
        dailyRepository.deleteAll();
    }

    @Test
    void should_fetchAll() throws Exception {
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

        MvcResult mvcResult = mockMvc.perform(get("/sc2/daily")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        DailyResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                DailyResponseDto[].class);

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(daily2.getType(), actual[0].type());
        Assertions.assertEquals(daily2.getDate(), actual[0].date());
        Assertions.assertEquals(daily2.getWins(), actual[0].wins());
        Assertions.assertEquals(daily2.getLosses(), actual[0].losses());

        Assertions.assertEquals(daily1.getType(), actual[1].type());
        Assertions.assertEquals(daily1.getDate(), actual[1].date());
        Assertions.assertEquals(daily1.getWins(), actual[1].wins());
        Assertions.assertEquals(daily1.getLosses(), actual[1].losses());

        Assertions.assertEquals(daily.getType(), actual[2].type());
        Assertions.assertEquals(daily.getDate(), actual[2].date());
        Assertions.assertEquals(daily.getWins(), actual[2].wins());
        Assertions.assertEquals(daily.getLosses(), actual[2].losses());
    }
}
