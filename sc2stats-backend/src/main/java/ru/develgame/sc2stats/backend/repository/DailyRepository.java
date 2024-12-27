package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.backend.entity.Daily;

import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {
    Daily findByDateAndType(String date, String type);

    List<Daily> findAllByOrderByTimestampDesc();
}
