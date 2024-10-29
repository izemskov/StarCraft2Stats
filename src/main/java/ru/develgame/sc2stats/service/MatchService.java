package ru.develgame.sc2stats.service;

import ru.develgame.sc2stats.entity.SC2Match;

import java.util.List;

public interface MatchService {
    List<SC2Match> fetchAllMatches();
}
