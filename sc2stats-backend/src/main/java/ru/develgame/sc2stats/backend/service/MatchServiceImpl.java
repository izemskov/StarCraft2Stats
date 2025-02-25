package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.repository.MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    @Override
    public List<Match> fetchAllMatchesSortedByDateDesc(MatchType type, MatchDecision decision) {
        Match match = new Match();
        if (type != null && type != MatchType.TYPE_NONE) {
            match.setType(type.getEntityValue());
        }
        if (decision != null && decision != MatchDecision.NONE) {
            match.setDecision(decision.getEntityValue());
        }
        Example<Match> example = Example.of(match);
        return matchRepository.findAll(example, Sort.by(Sort.Direction.DESC, Match.Fields.date));
    }
}
