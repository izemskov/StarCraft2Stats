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
import ru.develgame.sc2stats.frontend.dto.MatchesResponseDto;
import ru.develgame.sc2stats.frontend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;
import ru.develgame.sc2stats.frontend.exception.GetDataException;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final RestTemplate restTemplate;

    @Value("${sc.backend.baseUrl}")
    private final String backendBaseUrl;

    public MatchesResponseDto fetchAll(MatchType type, MatchDecision decision, int page, int size) {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + "/sc2/match");
            if (type != null) {
                uriBuilder.queryParam("type", type.name());
            }
            if (decision != null) {
                uriBuilder.queryParam("decision", decision.name());
            }
            uriBuilder.queryParam("page", page);
            uriBuilder.queryParam("size", size);

            ResponseEntity<MatchesResponseDto> response = restTemplate.getForEntity(uriBuilder.encode().toUriString(),
                    MatchesResponseDto.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new GetDataException("Cannot get match history. Error: %d".formatted(response.getStatusCode().value()));
            }

            if (response.getBody() == null) {
                throw new GetDataException("Cannot get match history. Empty response");
            }

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            throw new GetDataException(ex.getMessage());
        }
    }
}
