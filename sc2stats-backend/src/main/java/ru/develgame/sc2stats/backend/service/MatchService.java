package ru.develgame.sc2stats.backend.service;

import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Match;

import java.util.List;

public interface MatchService {
    List<Match> fetchAllMatchesSortedByDateDesc(MatchType type, MatchDecision decision);
}
