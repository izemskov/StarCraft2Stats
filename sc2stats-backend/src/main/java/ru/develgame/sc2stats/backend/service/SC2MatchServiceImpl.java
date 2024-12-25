package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.entity.SC2Match;
import ru.develgame.sc2stats.backend.repository.SC2MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SC2MatchServiceImpl implements SC2MatchService {
    private final SC2MatchRepository sc2MatchRepository;

    @Override
    public List<SC2Match> fetchAllMatchesSortedByDateDesc() {
        return sc2MatchRepository.findAllByOrderByDateDesc();
    }
}
