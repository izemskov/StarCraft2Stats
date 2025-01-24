package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.mapper.PlayerMapper;
import ru.develgame.sc2stats.backend.service.PlayerService;

@RestController
@RequestMapping("/sc2/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @GetMapping
    public ResponseEntity<PlayerResponseDto> fetchOne() {
        Player player = playerService.getCurrentPlayer();
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playerMapper.toDto(player));
    }
}
