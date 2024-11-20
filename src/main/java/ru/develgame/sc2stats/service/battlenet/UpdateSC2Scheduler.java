package ru.develgame.sc2stats.service.battlenet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateSC2Scheduler {
    private final UpdateSC2MatchHistoryService updateSC2MatchHistoryService;
    private final UpdateSC2PlayerInfo updateSC2PlayerInfo;
    private final BattleNetUpdateDateService battleNetUpdateDateService;
    private final BattleNetApiAuthService battleNetApiAuthService;

    @Scheduled(fixedRateString = "3600000", initialDelayString = "5000")
    public void updateSC2MatchHistory() {
        String accessToken = battleNetApiAuthService.getAccessToken();

        updateSC2MatchHistoryService.updateSC2MatchHistory(accessToken);
        updateSC2PlayerInfo.updateSC2PlayerInfo(accessToken);

        battleNetUpdateDateService.updateLastUpdateDate();
    }
}
