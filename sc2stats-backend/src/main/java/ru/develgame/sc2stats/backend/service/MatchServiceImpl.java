package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.bean.MatchesPage;
import ru.develgame.sc2stats.backend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.repository.MatchRepository;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    @Override
    public MatchesPage fetchAllMatchesSortedByDateDesc(MatchType type,
                                                       MatchDecision decision,
                                                       int page,
                                                       int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, Match.Fields.date));

        Match match = new Match();
        if (type != null && type != MatchType.TYPE_NONE) {
            match.setType(type.getEntityValue());
        }
        if (decision != null && decision != MatchDecision.NONE) {
            match.setDecision(decision.getEntityValue());
        }
        Example<Match> example = Example.of(match);
        Page<Match> matches = matchRepository.findAll(example, pageable);

        return MatchesPage.builder()
                .total(matches.getTotalElements())
                .matches(matches.getContent())
                .build();
    }
}
