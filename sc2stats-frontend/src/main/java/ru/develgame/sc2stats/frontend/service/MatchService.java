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
import org.springframework.web.util.UriComponentsBuilder;
import ru.develgame.sc2stats.frontend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.dto.MatchResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final RestTemplate restTemplate;

    @Value("${sc.backend.baseUrl}")
    private final String backendBaseUrl;

    public List<MatchResponseDto> fetchAll(MatchType type, MatchDecision decision) {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + "/sc2/match");
            if (type != null) {
                uriBuilder.queryParam("type", type.name());
            }
            if (decision != null) {
                uriBuilder.queryParam("decision", decision.name());
            }

            ResponseEntity<MatchResponseDto[]> response = restTemplate.getForEntity(uriBuilder.encode().toUriString(),
                    MatchResponseDto[].class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new GetDataException("Cannot get match history. Error: %d".formatted(response.getStatusCode().value()));
            }

            if (response.getBody() == null) {
                throw new GetDataException("Cannot get match history. Empty response");
            }

            return List.of(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            throw new GetDataException(ex.getMessage());
        }
    }
}
