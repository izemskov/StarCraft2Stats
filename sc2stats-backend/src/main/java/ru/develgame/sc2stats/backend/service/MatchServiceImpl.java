package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.repository.MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    @Override
    public List<Match> fetchAllMatchesSortedByDateDesc() {
        return matchRepository.findAllByOrderByDateDesc();
    }
}
