package ru.develgame.sc2stats.backend.service;

import ru.develgame.sc2stats.backend.bean.MatchesPage;
import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;

public interface MatchService {
    MatchesPage fetchAllMatchesSortedByDateDesc(MatchType type,
                                                MatchDecision decision,
                                                int page,
                                                int size);
}
