package ru.develgame.sc2stats.backend.integration.contoller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.develgame.sc2stats.backend.integration.configuration.BaseRestControllerIT;
import ru.develgame.sc2stats.backend.service.battlenet.UpdateDateService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UpdateDateControllerIT extends BaseRestControllerIT {
    @Autowired
    private UpdateDateService updateDateService;

    @Test
    @Order(1)
    void should_updateDateNotFound() throws Exception {
         mockMvc.perform(get("/sc2/lastUpdate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @Order(2)
    void should_getUpdateDate() throws Exception {
        updateDateService.updateLastUpdateDate();

        mockMvc.perform(get("/sc2/lastUpdate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
