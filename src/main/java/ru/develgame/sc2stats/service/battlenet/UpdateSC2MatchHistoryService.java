package ru.develgame.sc2stats.service.battlenet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.dto.BattleNetApiSC2MatchResponseDto;
import ru.develgame.sc2stats.dto.BattleNetApiSC2MatchesListResponseDto;
import ru.develgame.sc2stats.entity.SC2Match;
import ru.develgame.sc2stats.repository.SC2MatchRepository;
import ru.develgame.sc2stats.service.DailyService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateSC2MatchHistoryService {
    private final RestTemplate restTemplate;
    private final SC2MatchRepository sc2MatchRepository;
    private final DailyService dailyService;

    public void updateSC2MatchHistory(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BattleNetApiSC2MatchesListResponseDto> response = restTemplate.exchange(
                "https://eu.api.blizzard.com/sc2/legacy/profile/2/2/823560/matches", HttpMethod.GET, request,
                BattleNetApiSC2MatchesListResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().matches() == null) {
            log.warn(String.format("Cannot get data from Battle.net (matches). Code: %d", response.getStatusCode().value()));
            return;
        }

        for (BattleNetApiSC2MatchResponseDto match : response.getBody().matches()) {
            SC2Match sc2Match = sc2MatchRepository.findByDate(match.date());
            if (sc2Match != null
                    && Objects.equals(match.map(), sc2Match.getMap())
                    && Objects.equals(match.decision(), sc2Match.getDecision())
                    && Objects.equals(match.speed(), sc2Match.getSpeed())
                    && Objects.equals(match.type(), sc2Match.getType())) {
                continue;
            }

            dailyService.updateDaily(sc2MatchRepository.save(SC2Match.builder()
                    .map(match.map())
                    .decision(match.decision())
                    .speed(match.speed())
                    .type(match.type())
                    .date(match.date())
                    .build()));
        }
    }
}
