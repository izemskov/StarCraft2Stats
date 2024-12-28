package ru.develgame.sc2stats.backend.integration.configuration;

import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.TestConfiguration;

import java.net.ServerSocket;

@TestConfiguration
public class TestExternalServiceConfiguration {

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
