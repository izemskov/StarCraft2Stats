package ru.develgame.sc2stats.backend.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.sc2stats.backend.dto.MapResponseDto;
import ru.develgame.sc2stats.backend.entity.Map;

@Component
public class MapMapper {
    public MapResponseDto toDto(Map dbBean) {
        return MapResponseDto.builder()
                .name(dbBean.getName())
                .type(dbBean.getType())
                .wins(dbBean.getWins())
                .losses(dbBean.getLosses())
                .winRate(dbBean.getWinRate())
                .build();
    }
}
