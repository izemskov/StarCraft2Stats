package ru.develgame.sc2stats.backend.service;

import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.entity.Match;

import java.util.List;

public interface DailyService {
    void updateDaily(Match match);

    List<Daily> fetchAllSortedByDateDesc();
}
