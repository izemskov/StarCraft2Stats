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
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.dto.DailyResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {
    private final RestTemplate restTemplate;

    @Value("${sc.backend.baseUrl}")
    private final String backendBaseUrl;

    public List<DailyResponseDto> fetchAll() {
        try {
            ResponseEntity<DailyResponseDto[]> response = restTemplate.getForEntity(backendBaseUrl + "/sc2/daily",
                    DailyResponseDto[].class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new GetDataException("Cannot get daily. Error: %d".formatted(response.getStatusCode().value()));
            }

            if (response.getBody() == null) {
                throw new GetDataException("Cannot get daily. Empty response");
            }

            return List.of(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            throw new GetDataException(ex.getMessage());
        }
    }
}
