package ru.develgame.sc2stats.backend.service.battlenet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.backend.dto.battlenet.BattleNetApiMatchResponseDto;
import ru.develgame.sc2stats.backend.dto.battlenet.BattleNetApiMatchesListResponseDto;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.repository.MatchRepository;
import ru.develgame.sc2stats.backend.service.DailyService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateMatchHistoryService {
    private final RestTemplate restTemplate;
    private final MatchRepository matchRepository;
    private final DailyService dailyService;
    @Value("${sc.playerId}")
    private final String playerId;

    private String baseUrl = "https://eu.api.blizzard.com";

    public void updateMatchHistory(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BattleNetApiMatchesListResponseDto> response = restTemplate.exchange(
                baseUrl + "/sc2/legacy/profile/2/2/%s/matches".formatted(playerId), HttpMethod.GET, request,
                BattleNetApiMatchesListResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().matches() == null) {
            log.warn(String.format("Cannot get data from Battle.net (matches). Code: %d",
                    response.getStatusCode().value()));
            return;
        }

        for (BattleNetApiMatchResponseDto matchDto : response.getBody().matches()) {
            Match match = matchRepository.findByDate(matchDto.date());
            if (match != null
                    && Objects.equals(matchDto.map(), match.getMap())
                    && Objects.equals(matchDto.decision(), match.getDecision())
                    && Objects.equals(matchDto.speed(), match.getSpeed())
                    && Objects.equals(matchDto.type(), match.getType())) {
                continue;
            }

            dailyService.updateDaily(matchRepository.save(Match.builder()
                    .map(matchDto.map())
                    .decision(matchDto.decision())
                    .speed(matchDto.speed())
                    .type(matchDto.type())
                    .date(matchDto.date())
                    .build()));
        }
    }
}
