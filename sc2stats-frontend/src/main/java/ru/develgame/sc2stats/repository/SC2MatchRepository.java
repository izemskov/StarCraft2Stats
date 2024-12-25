package ru.develgame.sc2stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.entity.SC2Match;

import java.util.List;

@Repository
public interface SC2MatchRepository extends JpaRepository<SC2Match, Long> {
    SC2Match findByDate(Long date);

    List<SC2Match> findAllByOrderByDateDesc();
}
