package ru.develgame.sc2stats.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.sc2stats.backend.service.battlenet.UpdateDateService;

import java.util.Date;

@RestController
@RequestMapping("/sc2/lastUpdate")
@RequiredArgsConstructor
public class UpdateDateController {
    private final UpdateDateService updateDateService;

    @GetMapping
    public ResponseEntity<Date> getLastUpdateDate() {
        Date lastUpdateDate = updateDateService.getLastUpdateDate();
        if (lastUpdateDate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lastUpdateDate);
    }
}
