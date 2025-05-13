package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.bean.MatchesPage;
import ru.develgame.sc2stats.backend.dto.MatchesResponseDto;
import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.mapper.MatchMapper;
import ru.develgame.sc2stats.backend.service.MatchService;

@RestController
@RequestMapping("/sc2/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @GetMapping
    public ResponseEntity<MatchesResponseDto> fetchAll(@RequestParam(required = false) MatchType type,
                                                       @RequestParam(required = false) MatchDecision decision,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "25") int size) {
        MatchesPage matchesPage = matchService.fetchAllMatchesSortedByDateDesc(type, decision, page, size);

        return ResponseEntity.ok(MatchesResponseDto.builder()
                .total(matchesPage.total())
                .matches(matchesPage.matches().stream().map(matchMapper::toDto).toList())
                .build());
    }
}
