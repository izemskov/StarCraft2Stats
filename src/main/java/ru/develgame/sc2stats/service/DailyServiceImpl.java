package ru.develgame.sc2stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.entity.SC2Daily;
import ru.develgame.sc2stats.entity.SC2Match;
import ru.develgame.sc2stats.repository.SC2DailyRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {
    private final SC2DailyRepository sc2DailyRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    public void updateDaily(SC2Match sc2Match) {
        String date = dateFormat.format(new Date(sc2Match.getDate() * 1000L));
        SC2Daily sc2Daily = sc2DailyRepository.findByDateAndType(date, sc2Match.getType());
        if (sc2Daily == null) {
            sc2Daily = new SC2Daily();
            sc2Daily.setDate(date);
            sc2Daily.setType(sc2Match.getType());
            sc2Daily.setTimestamp(sc2Match.getDate());
        }

        if (sc2Match.getDecision().equals("Win")) {
            sc2Daily.setWins(sc2Daily.getWins() + 1);
        } else {
            sc2Daily.setLosses(sc2Daily.getLosses() + 1);
        }

        sc2DailyRepository.save(sc2Daily);
    }

    @Override
    public List<SC2Daily> fetchAllSortedByDateDesc() {
        return sc2DailyRepository.findAllByOrderByTimestampDesc();
    }
}
