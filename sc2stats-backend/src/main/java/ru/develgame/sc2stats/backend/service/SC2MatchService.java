package ru.develgame.sc2stats.backend.service;

import ru.develgame.sc2stats.backend.entity.SC2Match;

import java.util.List;

public interface SC2MatchService {
    List<SC2Match> fetchAllMatchesSortedByDateDesc();
}
