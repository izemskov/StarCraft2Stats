package ru.develgame.sc2stats.backend.service.battlenet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.backend.dto.battlenet.player.common.BattleNetApiAllPlayerInfoResponseDto;
import ru.develgame.sc2stats.backend.dto.battlenet.player.ladder.BattleNetApiLadderResponseDto;
import ru.develgame.sc2stats.backend.dto.battlenet.player.ladder.BattleNetApiLadderTeamResponseDto;
import ru.develgame.sc2stats.backend.dto.battlenet.player.ladder.BattleNetApiPlayerLadderResponseDto;
import ru.develgame.sc2stats.backend.dto.battlenet.player.ladder.BattleNetApiPlayerLadderShowcaseResponseDto;
import ru.develgame.sc2stats.backend.entity.MatchMakingRating;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.repository.MatchMakingRatingRepository;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;

import java.util.Date;
import java.util.List;

import static ru.develgame.sc2stats.backend.utils.BattleNetConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatePlayerInfoService {
    private final RestTemplate restTemplate;
    private final PlayerRepository playerRepository;
    private final MatchMakingRatingRepository matchMakingRatingRepository;
    @Value("${sc.playerId}")
    private final String playerId;

    private String baseUrl = "https://eu.api.blizzard.com";

    public void updatePlayerInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        Player player;
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            player = playerRepository.save(new Player());
        } else {
            player = players.get(0);
        }

        updatePlayerCommonInfo(request, player);
        updatePlayerMMR(request, player);

        playerRepository.save(player);
    }

    private void updatePlayerCommonInfo(HttpEntity<MultiValueMap<String, String>> request, Player player) {
        ResponseEntity<BattleNetApiAllPlayerInfoResponseDto> response = restTemplate.exchange(
                baseUrl + "/sc2/profile/2/2/%s?locale=en_US".formatted(playerId), HttpMethod.GET, request,
                BattleNetApiAllPlayerInfoResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().career() == null) {
            log.warn(String.format("Cannot get data from Battle.net (profile). Code: %d",
                    response.getStatusCode().value()));
            return;
        }

        if (response.getBody().career().totalCareerGames() != 0) {
            player.setTotalCareerGames(response.getBody().career().totalCareerGames());
        }
        if (response.getBody().career().best1v1Finish() != null) {
            player.setBest1v1FinishLeagueName(response.getBody().career().best1v1Finish().leagueName());
            player.setBest1v1FinishTimesAchieved(response.getBody().career().best1v1Finish().timesAchieved());
        }
        if (response.getBody().career().bestTeamFinish() != null) {
            player.setBestTeamFinishLeagueName(response.getBody().career().bestTeamFinish().leagueName());
            player.setBestTeamFinishTimesAchieved(response.getBody().career().bestTeamFinish().timesAchieved());
        }
    }

    private void updatePlayerMMR(HttpEntity<MultiValueMap<String, String>> request, Player player) {
        List<BattleNetApiPlayerLadderShowcaseResponseDto> playerLadders = getPlayerLadders(request);
        if (playerLadders == null) {
            return;
        }
        processAllPlayerLadders(request, playerLadders, player);
    }

    private List<BattleNetApiPlayerLadderShowcaseResponseDto> getPlayerLadders(HttpEntity<MultiValueMap<String, String>> request) {
        ResponseEntity<BattleNetApiPlayerLadderResponseDto> response = restTemplate.exchange(
                baseUrl + "/sc2/profile/2/2/%s/ladder/summary?locale=en_US".formatted(playerId), HttpMethod.GET,
                request, BattleNetApiPlayerLadderResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().allLadderMemberships() == null) {
            log.warn(String.format("Cannot get data from Battle.net (player ladders). Code: %d",
                    response.getStatusCode().value()));
            return null;
        }

        return response.getBody().allLadderMemberships();
    }

    private void processAllPlayerLadders(HttpEntity<MultiValueMap<String, String>> request,
                                         List<BattleNetApiPlayerLadderShowcaseResponseDto> playerLadders,
                                         Player player) {
        for (BattleNetApiPlayerLadderShowcaseResponseDto playerLadder : playerLadders) {
            if (playerLadder.localizedGameMode().contains(BATTLE_NET_TYPE_1v1)) {
                int mmr = processPlayerLadder(request, playerLadder);
                if (mmr != 0) {
                    if (player.getCurrentMMR() != mmr) {
                        createMMRHistory(BATTLE_NET_TYPE_1v1, mmr);
                        player.setCurrentMMR(mmr);
                    }
                    if (mmr > player.getBestMMR()) {
                        player.setBestMMR(mmr);
                    }
                }
            } else if (playerLadder.localizedGameMode().contains(BATTLE_NET_TYPE_2v2)) {
                int mmr = processPlayerLadder(request, playerLadder);
                if (mmr != 0) {
                    if (player.getCurrentMMR2x2() != mmr) {
                        createMMRHistory(BATTLE_NET_TYPE_2v2, mmr);
                        player.setCurrentMMR2x2(mmr);
                    }
                    if (mmr > player.getBestMMR2x2()) {
                        player.setBestMMR2x2(mmr);
                    }
                }
            } else if (playerLadder.localizedGameMode().contains(BATTLE_NET_TYPE_3v3)) {
                int mmr = processPlayerLadder(request, playerLadder);
                if (mmr != 0) {
                    if (player.getCurrentMMR3x3() != mmr) {
                        createMMRHistory(BATTLE_NET_TYPE_3v3, mmr);
                        player.setCurrentMMR3x3(mmr);
                    }
                    if (mmr > player.getBestMMR3x3()) {
                        player.setBestMMR3x3(mmr);
                    }
                }
            }
        }
    }

    private int processPlayerLadder(HttpEntity<MultiValueMap<String, String>> request,
                                     BattleNetApiPlayerLadderShowcaseResponseDto playerLadder) {
        ResponseEntity<BattleNetApiLadderResponseDto> response = restTemplate.exchange(
                baseUrl + "/sc2/profile/2/2/%s/ladder/%s?locale=en_US".formatted(playerId, playerLadder.ladderId()),
                HttpMethod.GET, request, BattleNetApiLadderResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().ladderTeams() == null) {
            log.warn(String.format("Cannot get data from Battle.net (ladder). Code: %d",
                    response.getStatusCode().value()));
            return 0;
        }

        for (BattleNetApiLadderTeamResponseDto ladderTeam : response.getBody().ladderTeams()) {
            if (ladderTeam.teamMembers().stream().filter(t -> t.id().equals(playerId)).findFirst().isPresent()) {
                return ladderTeam.mmr();
            }
        }

        return 0;
    }

    private void createMMRHistory(String type, int value) {
        MatchMakingRating mmr = new MatchMakingRating();
        mmr.setCreatedAt(new Date());
        mmr.setType(type);
        mmr.setMmrValue(value);
        matchMakingRatingRepository.save(mmr);
    }
}
