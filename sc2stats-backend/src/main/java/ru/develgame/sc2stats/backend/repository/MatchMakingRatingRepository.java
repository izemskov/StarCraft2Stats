package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.backend.entity.MatchMakingRating;

@Repository
public interface MatchMakingRatingRepository extends JpaRepository<MatchMakingRating, Long> {
}
