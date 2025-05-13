package ru.develgame.sc2stats.backend.integration.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.develgame.sc2stats.backend.bean.MatchesPage;
import ru.develgame.sc2stats.backend.dto.MatchResponseDto;
import ru.develgame.sc2stats.backend.dto.MatchesResponseDto;
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

        MatchesResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(3, actual.total());
        Assertions.assertEquals(match3.getMap(), actual.matches().get(0).map());
        Assertions.assertEquals(match3.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match3.getDecision(), actual.matches().get(0).decision());
        Assertions.assertEquals(match3.getType(), actual.matches().get(0).type());

        Assertions.assertEquals(match2.getMap(), actual.matches().get(1).map());
        Assertions.assertEquals(match2.getDate(), actual.matches().get(1).date());
        Assertions.assertEquals(match2.getDecision(), actual.matches().get(1).decision());
        Assertions.assertEquals(match2.getType(), actual.matches().get(1).type());

        Assertions.assertEquals(match.getMap(), actual.matches().get(2).map());
        Assertions.assertEquals(match.getDate(), actual.matches().get(2).date());
        Assertions.assertEquals(match.getDecision(), actual.matches().get(2).decision());
        Assertions.assertEquals(match.getType(), actual.matches().get(2).type());
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

        MatchesResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(3, actual.total());
        Assertions.assertEquals(match3.getMap(), actual.matches().get(0).map());
        Assertions.assertEquals(match3.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match3.getDecision(), actual.matches().get(0).decision());
        Assertions.assertEquals(match3.getType(), actual.matches().get(0).type());

        Assertions.assertEquals(match2.getMap(), actual.matches().get(1).map());
        Assertions.assertEquals(match2.getDate(), actual.matches().get(1).date());
        Assertions.assertEquals(match2.getDecision(), actual.matches().get(1).decision());
        Assertions.assertEquals(match2.getType(), actual.matches().get(1).type());

        Assertions.assertEquals(match.getMap(), actual.matches().get(2).map());
        Assertions.assertEquals(match.getDate(), actual.matches().get(2).date());
        Assertions.assertEquals(match.getDecision(), actual.matches().get(2).decision());
        Assertions.assertEquals(match.getType(), actual.matches().get(2).type());
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

        MatchesResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(2, actual.total());
        Assertions.assertEquals(match4.getMap(), actual.matches().get(0).map());
        Assertions.assertEquals(match4.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match4.getDecision(), actual.matches().get(0).decision());
        Assertions.assertEquals(match4.getType(), actual.matches().get(0).type());

        Assertions.assertEquals(match3.getMap(), actual.matches().get(1).map());
        Assertions.assertEquals(match3.getDate(), actual.matches().get(1).date());
        Assertions.assertEquals(match3.getDecision(), actual.matches().get(1).decision());
        Assertions.assertEquals(match3.getType(), actual.matches().get(1).type());
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

        MatchesResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(2, actual.total());
        Assertions.assertEquals(match4.getMap(), actual.matches().get(0).map());
        Assertions.assertEquals(match4.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match4.getDecision(), actual.matches().get(0).decision());
        Assertions.assertEquals(match4.getType(), actual.matches().get(0).type());

        Assertions.assertEquals(match3.getMap(), actual.matches().get(1).map());
        Assertions.assertEquals(match3.getDate(), actual.matches().get(1).date());
        Assertions.assertEquals(match3.getDecision(), actual.matches().get(1).decision());
        Assertions.assertEquals(match3.getType(), actual.matches().get(1).type());
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

        MatchesResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(1, actual.total());
        Assertions.assertEquals(match3.getMap(), actual.matches().get(0).map());
        Assertions.assertEquals(match3.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match3.getDecision(), actual.matches().get(0).decision());
        Assertions.assertEquals(match3.getType(), actual.matches().get(0).type());
    }

    @Test
    void should_fetchAll_whenPage() throws Exception {
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
                        .queryParam("size", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MatchesResponseDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(5, actual.total());
        Assertions.assertEquals(3, actual.matches().size());
        Assertions.assertEquals(match5.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match4.getDate(), actual.matches().get(1).date());
        Assertions.assertEquals(match3.getDate(), actual.matches().get(2).date());

        mvcResult = mockMvc.perform(get("/sc2/match")
                        .queryParam("page", "1")
                        .queryParam("size", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MatchesResponseDto.class);

        Assertions.assertEquals(5, actual.total());
        Assertions.assertEquals(2, actual.matches().size());
        Assertions.assertEquals(match2.getDate(), actual.matches().get(0).date());
        Assertions.assertEquals(match.getDate(), actual.matches().get(1).date());
    }
}
