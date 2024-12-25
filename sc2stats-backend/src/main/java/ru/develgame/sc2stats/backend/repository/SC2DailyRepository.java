package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.backend.entity.SC2Daily;

import java.util.List;

@Repository
public interface SC2DailyRepository extends JpaRepository<SC2Daily, Long> {
    SC2Daily findByDateAndType(String date, String type);

    List<SC2Daily> findAllByOrderByTimestampDesc();
}
