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
import ru.develgame.sc2stats.frontend.dto.MapResponseDto;
import ru.develgame.sc2stats.frontend.dto.filter.Actual;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;
import ru.develgame.sc2stats.frontend.exception.GetDataException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final RestTemplate restTemplate;

    @Value("${sc.backend.baseUrl}")
    private final String backendBaseUrl;

    public List<MapResponseDto> fetchAll(MatchType type, Actual actual) {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + "/sc2/map");
            if (type != null) {
                uriBuilder.queryParam("type", type.name());
            }
            if (actual != null && actual != Actual.NONE) {
                uriBuilder.queryParam("actual", actual == Actual.TRUE ? true : false);
            }

            ResponseEntity<MapResponseDto[]> response = restTemplate.getForEntity(uriBuilder.encode().toUriString(),
                    MapResponseDto[].class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new GetDataException("Cannot get maps stats. Error: %d".formatted(response.getStatusCode().value()));
            }

            if (response.getBody() == null) {
                throw new GetDataException("Cannot get maps stats. Empty response");
            }

            return List.of(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            throw new GetDataException(ex.getMessage());
        }
    }
}
