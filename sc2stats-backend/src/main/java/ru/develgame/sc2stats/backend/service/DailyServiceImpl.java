package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.entity.Daily;
import ru.develgame.sc2stats.backend.entity.Match;
import ru.develgame.sc2stats.backend.repository.DailyRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ru.develgame.sc2stats.backend.utils.BattleNetConst.BATTLE_NET_MATCH_DECISION_WIN;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {
    private final DailyRepository dailyRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    public void updateDaily(Match match) {
        String date = dateFormat.format(new Date(match.getDate() * 1000L));
        Daily daily = dailyRepository.findByDateAndType(date, match.getType());
        if (daily == null) {
            daily = new Daily();
            daily.setDate(date);
            daily.setType(match.getType());
            daily.setTimestamp(match.getDate());
        }

        if (match.getDecision().equals(BATTLE_NET_MATCH_DECISION_WIN)) {
            daily.setWins(daily.getWins() + 1);
        } else {
            daily.setLosses(daily.getLosses() + 1);
        }

        dailyRepository.save(daily);
    }

    @Override
    public List<Daily> fetchAllSortedByDateDesc() {
        return dailyRepository.findAllByOrderByTimestampDesc();
    }
}
