package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.dto.filter.MatchType;
import ru.develgame.sc2stats.backend.entity.Map;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.repository.MapRepository;

import java.util.List;

import static ru.develgame.sc2stats.backend.utils.BattleNetConst.BATTLE_NET_MATCH_DECISION_WIN;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;

    @Override
    public void updateMap(Match match) {
        Map map = mapRepository.findByNameAndType(match.getMap(), match.getType());
        if (map == null) {
            map = new Map();
            map.setName(match.getMap());
            map.setType(match.getType());
        }

        if (match.getDecision().equals(BATTLE_NET_MATCH_DECISION_WIN)) {
            map.setWins(map.getWins() + 1);
        } else {
            map.setLosses(map.getLosses() + 1);
        }
        mapRepository.save(map);
    }

    @Override
    public List<Map> fetchAll(MatchType type) {
        if (type == null || type == MatchType.TYPE_NONE) {
            return mapRepository.findAll();
        } else {
            return mapRepository.findAllByType(type.getEntityValue());
        }
    }
}
