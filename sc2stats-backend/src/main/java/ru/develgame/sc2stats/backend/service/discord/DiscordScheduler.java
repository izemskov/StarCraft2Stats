package ru.develgame.sc2stats.backend.service.discord;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.repository.DailyRepository;
import ru.develgame.sc2stats.backend.service.PlayerService;

import java.util.List;
import java.util.Map;

import static ru.develgame.sc2stats.backend.dto.filter.MatchType.*;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "discord.enabled", havingValue = "true")
public class DiscordScheduler {
    private final DailyRepository dailyRepository;
    private final PlayerService playerService;
    private final RestTemplate restTemplate;
    @Value("${discord.webhook}")
    private final String webhook;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendDailyStats() {
        List<Daily> dailies = dailyRepository.findAllBySendOrderByTimestampAsc(false);

        for (Daily daily : dailies) {
            int mmr = getCurrentMMRByType(daily.getType());
            sendMessage("""
                    ###################
                    Date: %s
                    Type: %s
                    Result: %s-%s
                    MMR: %d
                    """. formatted(daily.getDate(), daily.getType(), daily.getWins(), daily.getLosses(), mmr));
            daily.setSend(true);
            dailyRepository.save(daily);
        }
    }

    private void sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of("content", message);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        restTemplate.postForObject(webhook, request, String.class);
    }

    private int getCurrentMMRByType(String type) {
        Player currentPlayer = playerService.getCurrentPlayer();
        if (currentPlayer == null) {
            return 0;
        }
        if (TYPE_1X1.getEntityValue().equals(type)) {
            return currentPlayer.getCurrentMMR();
        } else if (TYPE_2X2.getEntityValue().equals(type)) {
            return currentPlayer.getCurrentMMR2x2();
        } else if (TYPE_3X3.getEntityValue().equals(type)) {
            return currentPlayer.getCurrentMMR3x3();
        }
        return 0;
    }
}
