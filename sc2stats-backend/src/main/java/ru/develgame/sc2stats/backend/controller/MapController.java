package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.dto.MapResponseDto;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.mapper.MapMapper;
import ru.develgame.sc2stats.backend.service.MapService;

import java.util.List;

@RestController
@RequestMapping("/sc2/map")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    private final MapMapper mapMapper;

    @GetMapping
    public ResponseEntity<List<MapResponseDto>> fetchAll(@RequestParam(required = false) MatchType type) {

        return ResponseEntity.ok(mapService.fetchAll(type).stream()
                .map(mapMapper::toDto)
                .toList());
    }
}
