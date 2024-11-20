package ru.develgame.sc2stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.entity.SC2Player;
import ru.develgame.sc2stats.repository.SC2PlayerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final SC2PlayerRepository sc2PlayerRepository;

    @Override
    public SC2Player getCurrentPlayer() {
        List<SC2Player> players = sc2PlayerRepository.findAll();
        if (players.isEmpty()) {
            return null;
        }
        return players.get(0);
    }
}
