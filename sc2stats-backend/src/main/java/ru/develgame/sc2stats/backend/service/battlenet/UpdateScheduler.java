package ru.develgame.sc2stats.backend.service.battlenet;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnExpression("${sc.update.enable:false}")
public class UpdateScheduler {
    private final UpdateMatchHistoryService updateMatchHistoryService;
    private final UpdatePlayerInfoService updatePlayerInfoService;
    private final UpdateDateService updateDateService;
    private final BattleNetApiAuthService battleNetApiAuthService;

    @Scheduled(fixedRateString = "600000", initialDelayString = "5000")
    public void updateSC2MatchHistory() {
        String accessToken = battleNetApiAuthService.getAccessToken();

        updateMatchHistoryService.updateMatchHistory(accessToken);
        updatePlayerInfoService.updatePlayerInfo(accessToken);

        updateDateService.updateLastUpdateDate();
    }
}
