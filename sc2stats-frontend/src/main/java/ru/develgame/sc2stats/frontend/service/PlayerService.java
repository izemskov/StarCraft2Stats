package ru.develgame.sc2stats.frontend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.frontend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.frontend.exception.GetDataException;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final RestTemplate restTemplate;

    @Value("${sc.backend.baseUrl}")
    private final String backendBaseUrl;

    public PlayerResponseDto fetchPlayerInfo() {
        try {
            ResponseEntity<PlayerResponseDto> response = restTemplate.getForEntity(backendBaseUrl + "/sc2/player",
                    PlayerResponseDto.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new GetDataException("Cannot get player info. Error: %d".formatted(response.getStatusCode().value()));
            }

            if (response.getBody() == null) {
                throw new GetDataException("Cannot get player info. Empty response");
            }

            return response.getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                return null;
            }
            throw new GetDataException(ex.getMessage());
        } catch (HttpServerErrorException | ResourceAccessException ex) {
            throw new GetDataException(ex.getMessage());
        }
    }
}
