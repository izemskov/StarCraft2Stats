package ru.develgame.sc2stats.backend.integration.configuration;

import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.backend.configuration.battlenetapi.BattleNetProperties;

import java.net.ServerSocket;

@TestConfiguration
@ComponentScan("ru.develgame.sc2stats.backend.service.battlenet")
public class TestExternalServiceConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BattleNetProperties battleNetProperties() {
        return new BattleNetProperties("clientId", "clientSecret");
    }

    public static ClientAndServer getStartedServer() {
        int attempts = 0;
        while (attempts++ < 4) {
            final int localPort;
            try {
                try (final ServerSocket serverSocket = new ServerSocket(0)) {
                    localPort = serverSocket.getLocalPort();
                }
                return ClientAndServer.startClientAndServer(localPort);
            } catch (Exception ex) {
            }
        }
        throw new IllegalStateException("Failed to start mock server");
    }
}
