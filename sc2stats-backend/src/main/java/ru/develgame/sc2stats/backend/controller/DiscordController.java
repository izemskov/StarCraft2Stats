package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.service.discord.DiscordScheduler;

@RestController
@RequestMapping("/discord")
@RequiredArgsConstructor
public class DiscordController {

    private final DiscordScheduler discordScheduler;

    @PostMapping("/send")
    public ResponseEntity<Void> send() {
        discordScheduler.sendDailyStats();
        return ResponseEntity.ok(null);
    }
}
