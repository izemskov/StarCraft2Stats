package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.dto.SC2MatchResponseDto;
import ru.develgame.sc2stats.backend.mapper.SC2MatchMapper;
import ru.develgame.sc2stats.backend.service.SC2MatchService;

import java.util.List;

@RestController
@RequestMapping("/sc2/match")
@RequiredArgsConstructor
public class SC2MatchController {
    private final SC2MatchService sc2MatchService;
    private final SC2MatchMapper sc2MatchMapper;

    @GetMapping
    public ResponseEntity<List<SC2MatchResponseDto>> fetchAll() {
        return ResponseEntity.ok(sc2MatchService.fetchAllMatchesSortedByDateDesc().stream()
                .map(sc2MatchMapper::toDto)
                .toList());
    }
}
