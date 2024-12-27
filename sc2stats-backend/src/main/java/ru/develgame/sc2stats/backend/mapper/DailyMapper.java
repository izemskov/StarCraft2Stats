package ru.develgame.sc2stats.backend.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.sc2stats.backend.dto.DailyResponseDto;
import ru.develgame.sc2stats.backend.entity.Daily;

@Component
public class DailyMapper {
    public DailyResponseDto toDto(Daily dbBean) {
        return DailyResponseDto.builder()
                .type(dbBean.getType())
                .wins(dbBean.getWins())
                .losses(dbBean.getLosses())
                .date(dbBean.getDate())
                .build();
    }
}
