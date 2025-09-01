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
import ru.develgame.sc2stats.backend.repository.DailyRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "discord.enabled", havingValue = "true")
public class DiscordScheduler {
    private final DailyRepository dailyRepository;
    private final RestTemplate restTemplate;
    @Value("${discord.webhook}")
    private final String webhook;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendDailyStats() {
        List<Daily> dailies = dailyRepository.findAllBySendOrderByTimestampAsc(false);

        for (Daily daily : dailies) {
            sendMessage("""
                    ###################
                    Date: %s
                    Type: %s
                    Result: %s-%s
                    """. formatted(daily.getDate(), daily.getType(), daily.getWins(), daily.getLosses()));
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
}
