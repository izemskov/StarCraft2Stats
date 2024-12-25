package ru.develgame.sc2stats.backend.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.sc2stats.backend.dto.SC2MatchResponseDto;
import ru.develgame.sc2stats.backend.entity.SC2Match;

@Component
public class SC2MatchMapper {
    public SC2MatchResponseDto toDto(SC2Match dbBean) {
        return SC2MatchResponseDto.builder()
                .map(dbBean.getMap())
                .type(dbBean.getType())
                .decision(dbBean.getDecision())
                .date(dbBean.getDate())
                .build();
    }
}
