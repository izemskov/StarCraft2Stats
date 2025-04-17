package ru.develgame.sc2stats.backend.service;

import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Map;
import ru.develgame.sc2stats.backend.entity.Match;

import java.util.List;

public interface MapService {
    void updateMap(Match match);

    List<Map> fetchAll(MatchType type);

    void updateMaps();
}
