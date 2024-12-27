package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.dto.DailyResponseDto;
import ru.develgame.sc2stats.backend.mapper.DailyMapper;
import ru.develgame.sc2stats.backend.service.DailyService;

import java.util.List;

@RestController
@RequestMapping("/sc2/daily")
@RequiredArgsConstructor
public class DailyController {
    private final DailyService dailyService;
    private final DailyMapper dailyMapper;

    @GetMapping
    public ResponseEntity<List<DailyResponseDto>> fetchAll() {
        return ResponseEntity.ok(dailyService.fetchAllSortedByDateDesc().stream()
                .map(dailyMapper::toDto)
                .toList());
    }
}
