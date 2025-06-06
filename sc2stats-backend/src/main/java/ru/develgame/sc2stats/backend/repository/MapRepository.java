package ru.develgame.sc2stats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.sc2stats.backend.entity.Map;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    Map findByNameAndType(String name, String type);

    List<Map> findAllByType(String type);

    List<Map> findAllByActual(boolean actual);

    List<Map> findAllByTypeAndActual(String type, boolean actual);
}
