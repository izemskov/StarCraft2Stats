package ru.develgame.sc2stats.backend.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.sc2stats.backend.dto.MatchResponseDto;
import ru.develgame.sc2stats.backend.entity.Match;

@Component
public class MatchMapper {
    public MatchResponseDto toDto(Match dbBean) {
        return MatchResponseDto.builder()
                .map(dbBean.getMap())
                .type(dbBean.getType())
                .decision(dbBean.getDecision())
                .date(dbBean.getDate())
                .build();
    }
}
