package ru.develgame.sc2stats.backend.integration.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.develgame.sc2stats.backend.dto.MatchResponseDto;
import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRestControllerIT;
import ru.develgame.sc2stats.backend.repository.MatchRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MatchControllerIT extends BaseRestControllerIT {
    @Autowired
    private MatchRepository matchRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void cleanUp() {
        matchRepository.deleteAll();
    }

    @Test
    void should_fetchAll() throws Exception {
        Match match = new Match();
        match.setDate(1000000L);
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/match")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MatchResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchResponseDto[].class);

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(match3.getMap(), actual[0].map());
        Assertions.assertEquals(match3.getDate(), actual[0].date());
        Assertions.assertEquals(match3.getDecision(), actual[0].decision());
        Assertions.assertEquals(match3.getType(), actual[0].type());

        Assertions.assertEquals(match2.getMap(), actual[1].map());
        Assertions.assertEquals(match2.getDate(), actual[1].date());
        Assertions.assertEquals(match2.getDecision(), actual[1].decision());
        Assertions.assertEquals(match2.getType(), actual[1].type());

        Assertions.assertEquals(match.getMap(), actual[2].map());
        Assertions.assertEquals(match.getDate(), actual[2].date());
        Assertions.assertEquals(match.getDecision(), actual[2].decision());
        Assertions.assertEquals(match.getType(), actual[2].type());
    }

    @Test
    void should_fetchAll_whenMatchesTypeNoneAndDecisionNone() throws Exception {
        Match match = new Match();
        match.setDate(1000000L);
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3 = matchRepository.save(match3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/match")
                        .queryParam("type", MatchType.TYPE_NONE.name())
                        .queryParam("decision", MatchDecision.NONE.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MatchResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchResponseDto[].class);

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(match3.getMap(), actual[0].map());
        Assertions.assertEquals(match3.getDate(), actual[0].date());
        Assertions.assertEquals(match3.getDecision(), actual[0].decision());
        Assertions.assertEquals(match3.getType(), actual[0].type());

        Assertions.assertEquals(match2.getMap(), actual[1].map());
        Assertions.assertEquals(match2.getDate(), actual[1].date());
        Assertions.assertEquals(match2.getDecision(), actual[1].decision());
        Assertions.assertEquals(match2.getType(), actual[1].type());

        Assertions.assertEquals(match.getMap(), actual[2].map());
        Assertions.assertEquals(match.getDate(), actual[2].date());
        Assertions.assertEquals(match.getDecision(), actual[2].decision());
        Assertions.assertEquals(match.getType(), actual[2].type());
    }

    @Test
    void should_fetchAll_whenMatchesByType() throws Exception {
        Match match = new Match();
        match.setDate(1000000L);
        match.setType("2v2");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2.setType("3v3");
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3.setType("1v1");
        match3 = matchRepository.save(match3);

        Match match4 = new Match();
        match4.setDate(4000000L);
        match4.setType("1v1");
        match4 = matchRepository.save(match4);

        Match match5 = new Match();
        match5.setDate(5000000L);
        match5 = matchRepository.save(match5);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/match")
                        .queryParam("type", MatchType.TYPE_1X1.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MatchResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchResponseDto[].class);

        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals(match4.getMap(), actual[0].map());
        Assertions.assertEquals(match4.getDate(), actual[0].date());
        Assertions.assertEquals(match4.getDecision(), actual[0].decision());
        Assertions.assertEquals(match4.getType(), actual[0].type());

        Assertions.assertEquals(match3.getMap(), actual[1].map());
        Assertions.assertEquals(match3.getDate(), actual[1].date());
        Assertions.assertEquals(match3.getDecision(), actual[1].decision());
        Assertions.assertEquals(match3.getType(), actual[1].type());
    }

    @Test
    void should_fetchAllMatchesByDecision() throws Exception {
        Match match = new Match();
        match.setDate(1000000L);
        match.setDecision("Loss");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2.setDecision("Loss");
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3.setDecision("Win");
        match3 = matchRepository.save(match3);

        Match match4 = new Match();
        match4.setDate(4000000L);
        match4.setDecision("Win");
        match4 = matchRepository.save(match4);

        Match match5 = new Match();
        match5.setDate(5000000L);
        match5 = matchRepository.save(match5);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/match")
                        .queryParam("decision", MatchDecision.WIN.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MatchResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchResponseDto[].class);

        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals(match4.getMap(), actual[0].map());
        Assertions.assertEquals(match4.getDate(), actual[0].date());
        Assertions.assertEquals(match4.getDecision(), actual[0].decision());
        Assertions.assertEquals(match4.getType(), actual[0].type());

        Assertions.assertEquals(match3.getMap(), actual[1].map());
        Assertions.assertEquals(match3.getDate(), actual[1].date());
        Assertions.assertEquals(match3.getDecision(), actual[1].decision());
        Assertions.assertEquals(match3.getType(), actual[1].type());
    }

    @Test
    void should_fetchAllMatchesByTypeAndDecision() throws Exception {
        Match match = new Match();
        match.setDate(1000000L);
        match.setType("2v2");
        match.setDecision("Loss");
        match = matchRepository.save(match);

        Match match2 = new Match();
        match2.setDate(2000000L);
        match2.setType("1v1");
        match2.setDecision("Loss");
        match2 = matchRepository.save(match2);

        Match match3 = new Match();
        match3.setDate(3000000L);
        match3.setType("1v1");
        match3.setDecision("Win");
        match3 = matchRepository.save(match3);

        Match match4 = new Match();
        match4.setDate(4000000L);
        match4.setType("2v2");
        match4.setDecision("Win");
        match4 = matchRepository.save(match4);

        Match match5 = new Match();
        match5.setDate(5000000L);
        match5 = matchRepository.save(match5);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/match")
                        .queryParam("type", MatchType.TYPE_1X1.name())
                        .queryParam("decision", MatchDecision.WIN.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MatchResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchResponseDto[].class);

        Assertions.assertEquals(1, actual.length);
        Assertions.assertEquals(match3.getMap(), actual[0].map());
        Assertions.assertEquals(match3.getDate(), actual[0].date());
        Assertions.assertEquals(match3.getDecision(), actual[0].decision());
        Assertions.assertEquals(match3.getType(), actual[0].type());
    }
}
