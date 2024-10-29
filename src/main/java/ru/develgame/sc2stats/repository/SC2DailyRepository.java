package ru.develgame.sc2stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.entity.SC2Daily;

@Repository
public interface SC2DailyRepository extends JpaRepository<SC2Daily, Long> {
    SC2Daily findByDateAndType(String date, String type);
}
