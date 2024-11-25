package ru.develgame.sc2stats.service.battlenet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.dto.player.common.BattleNetApiSC2AllPlayerInfoResponseDto;
import ru.develgame.sc2stats.dto.player.ladder.BattleNetApiSC2LadderResponseDto;
import ru.develgame.sc2stats.dto.player.ladder.BattleNetApiSC2LadderTeamResponseDto;
import ru.develgame.sc2stats.dto.player.ladder.BattleNetApiSC2PlayerLadderResponseDto;
import ru.develgame.sc2stats.dto.player.ladder.BattleNetApiSC2PlayerLadderShowcaseResponseDto;
import ru.develgame.sc2stats.entity.SC2Player;
import ru.develgame.sc2stats.repository.SC2PlayerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateSC2PlayerInfo {
    private final RestTemplate restTemplate;
    private final SC2PlayerRepository sc2PlayerRepository;

    public void updateSC2PlayerInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        SC2Player sc2Player;
        List<SC2Player> players = sc2PlayerRepository.findAll();
        if (players.isEmpty()) {
            sc2Player = sc2PlayerRepository.save(new SC2Player());
        } else {
            sc2Player = players.get(0);
        }

        updatePlayerCommonInfo(request, sc2Player);
        updatePlayerMMR(request, sc2Player);

        sc2PlayerRepository.save(sc2Player);
    }

    private void updatePlayerCommonInfo(HttpEntity<MultiValueMap<String, String>> request, SC2Player sc2Player) {
        ResponseEntity<BattleNetApiSC2AllPlayerInfoResponseDto> response = restTemplate.exchange(
                "https://eu.api.blizzard.com/sc2/profile/2/2/823560?locale=en_US", HttpMethod.GET, request,
                BattleNetApiSC2AllPlayerInfoResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().career() == null) {
            log.warn(String.format("Cannot get data from Battle.net (profile). Code: %d", response.getStatusCode().value()));
            return;
        }

        if (response.getBody().career().totalCareerGames() != 0) {
            sc2Player.setTotalCareerGames(response.getBody().career().totalCareerGames());
        }
        if (response.getBody().career().best1v1Finish() != null) {
            sc2Player.setBest1v1FinishLeagueName(response.getBody().career().best1v1Finish().leagueName());
            sc2Player.setBest1v1FinishTimesAchieved(response.getBody().career().best1v1Finish().timesAchieved());
        }
        if (response.getBody().career().bestTeamFinish() != null) {
            sc2Player.setBestTeamFinishLeagueName(response.getBody().career().bestTeamFinish().leagueName());
            sc2Player.setBestTeamFinishTimesAchieved(response.getBody().career().bestTeamFinish().timesAchieved());
        }
    }

    private void updatePlayerMMR(HttpEntity<MultiValueMap<String, String>> request, SC2Player sc2Player) {
        List<BattleNetApiSC2PlayerLadderShowcaseResponseDto> playerLadders = getPlayerLadders(request);
        if (playerLadders == null) {
            return;
        }
        processAllPlayerLadders(request, playerLadders, sc2Player);
    }

    private List<BattleNetApiSC2PlayerLadderShowcaseResponseDto> getPlayerLadders(HttpEntity<MultiValueMap<String, String>> request) {
        ResponseEntity<BattleNetApiSC2PlayerLadderResponseDto> response = restTemplate.exchange(
                "https://eu.api.blizzard.com/sc2/profile/2/2/823560/ladder/summary?locale=en_US", HttpMethod.GET, request,
                BattleNetApiSC2PlayerLadderResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().allLadderMemberships() == null) {
            log.warn(String.format("Cannot get data from Battle.net (player ladders). Code: %d", response.getStatusCode().value()));
            return null;
        }

        return response.getBody().allLadderMemberships();
    }

    private void processAllPlayerLadders(HttpEntity<MultiValueMap<String, String>> request,
                                         List<BattleNetApiSC2PlayerLadderShowcaseResponseDto> playerLadders,
                                         SC2Player sc2Player) {
        for (BattleNetApiSC2PlayerLadderShowcaseResponseDto playerLadder : playerLadders) {
            if (playerLadder.localizedGameMode().contains("1v1")) {
                int mmr = processPlayerLadder(request, playerLadder, sc2Player);
                if (mmr != 0) {
                    sc2Player.setCurrentMMR(mmr);
                    if (mmr > sc2Player.getBestMMR()) {
                        sc2Player.setBestMMR(mmr);
                    }
                }
            } else if (playerLadder.localizedGameMode().contains("2v2")) {
                int mmr = processPlayerLadder(request, playerLadder, sc2Player);
                if (mmr != 0) {
                    sc2Player.setCurrentMMR2x2(mmr);
                    if (mmr > sc2Player.getBestMMR2x2()) {
                        sc2Player.setBestMMR2x2(mmr);
                    }
                }
            }
        }
    }

    private int processPlayerLadder(HttpEntity<MultiValueMap<String, String>> request,
                                     BattleNetApiSC2PlayerLadderShowcaseResponseDto playerLadder,
                                     SC2Player sc2Player) {
        ResponseEntity<BattleNetApiSC2LadderResponseDto> response = restTemplate.exchange(
                "https://eu.api.blizzard.com/sc2/profile/2/2/823560/ladder/" + playerLadder.ladderId() + "?locale=en_US",
                HttpMethod.GET, request, BattleNetApiSC2LadderResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK
                || response.getBody() == null
                || response.getBody().ladderTeams() == null) {
            log.warn(String.format("Cannot get data from Battle.net (ladder). Code: %d", response.getStatusCode().value()));
            return 0;
        }

        for (BattleNetApiSC2LadderTeamResponseDto ladderTeam : response.getBody().ladderTeams()) {
            if (ladderTeam.teamMembers().stream().filter(t -> t.id().equals("823560")).findFirst().isPresent()) {
                return ladderTeam.mmr();
            }
        }

        return 0;
    }
}
