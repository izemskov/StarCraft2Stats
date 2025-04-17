package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.sc2stats.backend.entity.Map;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {
    Map findByNameAndType(String name, String type);

    List<Map> findAllByType(String type);
}
