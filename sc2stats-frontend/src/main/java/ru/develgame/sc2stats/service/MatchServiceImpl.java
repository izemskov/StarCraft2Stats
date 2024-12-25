package ru.develgame.sc2stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.entity.SC2Match;
import ru.develgame.sc2stats.repository.SC2MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final SC2MatchRepository sc2MatchRepository;

    @Override
    public List<SC2Match> fetchAllMatchesSortedByDateDesc() {
        return sc2MatchRepository.findAllByOrderByDateDesc();
    }
}
