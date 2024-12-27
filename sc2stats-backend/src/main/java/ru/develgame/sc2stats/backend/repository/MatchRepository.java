package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.backend.entity.Match;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Match findByDate(Long date);

    List<Match> findAllByOrderByDateDesc();
}
