package ru.develgame.sc2stats.backend.service;

import ru.develgame.sc2stats.backend.entity.Match;

import java.util.List;

public interface MatchService {
    List<Match> fetchAllMatchesSortedByDateDesc();
}
