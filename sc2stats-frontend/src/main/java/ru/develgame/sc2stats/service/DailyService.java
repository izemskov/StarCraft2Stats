package ru.develgame.sc2stats.service;

import ru.develgame.sc2stats.entity.SC2Daily;
import ru.develgame.sc2stats.entity.SC2Match;

import java.util.List;

public interface DailyService {
    void updateDaily(SC2Match sc2Match);

    List<SC2Daily> fetchAllSortedByDateDesc();
}
