package ru.develgame.sc2stats.backend.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.develgame.sc2stats.backend.integration.configuration.BaseServiceIT;
import ru.develgame.sc2stats.backend.integration.configuration.TestExternalServiceConfiguration;
import ru.develgame.sc2stats.backend.service.battlenet.BattleNetApiAuthService;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class BattleNetApiAuthServiceIT extends BaseServiceIT {
    @Autowired
    private BattleNetApiAuthService battleNetApiAuthService;

    private ClientAndServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = TestExternalServiceConfiguration.getStartedServer();

        ReflectionTestUtils.setField(battleNetApiAuthService, "baseUrl",
                "http://localhost:" + mockServer.getLocalPort());
    }

    @AfterEach
    public void cleanUp() {
        mockServer.stop();
    }

    @Test
    void should_getAccessToken() {
        mockServer.when(request()
                        .withMethod("POST")
                        .withPath("/token")
                        .withHeader("Authorization", "Basic Y2xpZW50SWQ6Y2xpZW50U2VjcmV0")
                )
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody("""
                                {
                                     "access_token": "AccessTokenValue",
                                     "token_type": "bearer",
                                     "expires_in": 86399,
                                     "sub": "SubValue"
                                }
                                """)
                        .withStatusCode(200));

        String accessToken = battleNetApiAuthService.getAccessToken();
        Assertions.assertEquals("AccessTokenValue", accessToken);
    }
}
