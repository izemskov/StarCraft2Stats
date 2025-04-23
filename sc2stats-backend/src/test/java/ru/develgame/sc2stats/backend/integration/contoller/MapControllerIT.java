package ru.develgame.sc2stats.backend.integration.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.develgame.sc2stats.backend.dto.MapResponseDto;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Map;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRestControllerIT;
import ru.develgame.sc2stats.backend.repository.MapRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MapControllerIT extends BaseRestControllerIT {
    @Autowired
    private MapRepository mapRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void cleanUp() {
        mapRepository.deleteAll();
    }

    @Test
    void should_fetchAll() throws Exception {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/map")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MapResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MapResponseDto[].class);

        Assertions.assertEquals(4, actual.length);
        Assertions.assertEquals(map.getName(), actual[0].name());
        Assertions.assertEquals(map.getType(), actual[0].type());
        Assertions.assertEquals(map1.getName(), actual[1].name());
        Assertions.assertEquals(map1.getType(), actual[1].type());
        Assertions.assertEquals(map2.getName(), actual[2].name());
        Assertions.assertEquals(map2.getType(), actual[2].type());
        Assertions.assertEquals(map3.getName(), actual[3].name());
        Assertions.assertEquals(map3.getType(), actual[3].type());
    }

    @Test
    void should_fetchAll_whenMatchesTypeNone() throws Exception {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/map")
                        .queryParam("type", MatchType.TYPE_NONE.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MapResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MapResponseDto[].class);

        Assertions.assertEquals(4, actual.length);
        Assertions.assertEquals(map.getName(), actual[0].name());
        Assertions.assertEquals(map.getType(), actual[0].type());
        Assertions.assertEquals(map1.getName(), actual[1].name());
        Assertions.assertEquals(map1.getType(), actual[1].type());
        Assertions.assertEquals(map2.getName(), actual[2].name());
        Assertions.assertEquals(map2.getType(), actual[2].type());
        Assertions.assertEquals(map3.getName(), actual[3].name());
        Assertions.assertEquals(map3.getType(), actual[3].type());
    }

    @Test
    void should_fetchAll_whenMatchesByType() throws Exception {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/map")
                        .queryParam("type", MatchType.TYPE_1X1.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MapResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MapResponseDto[].class);

        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals(map.getName(), actual[0].name());
        Assertions.assertEquals(map.getType(), actual[0].type());
        Assertions.assertEquals(map2.getName(), actual[1].name());
        Assertions.assertEquals(map2.getType(), actual[1].type());
    }

    @Test
    void should_fetchAll_whenMatchesByActual() throws Exception {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .actual(false)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/map")
                        .queryParam("actual", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MapResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MapResponseDto[].class);

        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals(map.getName(), actual[0].name());
        Assertions.assertEquals(map.getType(), actual[0].type());
        Assertions.assertEquals(map1.getName(), actual[1].name());
        Assertions.assertEquals(map1.getType(), actual[1].type());
    }

    @Test
    void should_fetchAll_whenMatchesByActualAndType() throws Exception {
        Map map = Map.builder()
                .name("name")
                .type("1v1")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map = mapRepository.save(map);

        Map map1 = Map.builder()
                .name("name")
                .type("2v2")
                .wins(5)
                .actual(true)
                .losses(10)
                .build();
        map1 = mapRepository.save(map1);

        Map map2 = Map.builder()
                .name("name1")
                .type("1v1")
                .wins(5)
                .actual(false)
                .losses(10)
                .build();
        map2 = mapRepository.save(map2);

        Map map3 = Map.builder()
                .name("name1")
                .type("2v2")
                .wins(5)
                .losses(10)
                .build();
        map3 = mapRepository.save(map3);

        MvcResult mvcResult = mockMvc.perform(get("/sc2/map")
                        .queryParam("type", MatchType.TYPE_1X1.name())
                        .queryParam("actual", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        MapResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                MapResponseDto[].class);

        Assertions.assertEquals(1, actual.length);
        Assertions.assertEquals(map.getName(), actual[0].name());
        Assertions.assertEquals(map.getType(), actual[0].type());
    }
}
