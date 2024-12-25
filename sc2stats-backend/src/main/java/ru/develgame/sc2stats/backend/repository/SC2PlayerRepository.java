package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.backend.entity.SC2Player;

@Repository
public interface SC2PlayerRepository extends JpaRepository<SC2Player, Long>  {
}
