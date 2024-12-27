package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.dto.MatchResponseDto;
import ru.develgame.sc2stats.backend.mapper.MatchMapper;
import ru.develgame.sc2stats.backend.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/sc2/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @GetMapping
    public ResponseEntity<List<MatchResponseDto>> fetchAll() {
        return ResponseEntity.ok(matchService.fetchAllMatchesSortedByDateDesc().stream()
                .map(matchMapper::toDto)
                .toList());
    }
}
