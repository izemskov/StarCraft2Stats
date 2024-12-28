package ru.develgame.sc2stats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.backend.entity.Player;
import ru.develgame.sc2stats.backend.repository.PlayerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    public Player getCurrentPlayer() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            return null;
        }
        return players.get(0);
    }
}
