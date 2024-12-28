package ru.develgame.sc2stats.backend.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.sc2stats.backend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.backend.entity.Player;

@Component
public class PlayerMapper {
    public PlayerResponseDto toDto(Player dbBean) {
        return PlayerResponseDto.builder()
                .totalCareerGames(dbBean.getTotalCareerGames())
                .best1v1FinishLeagueName(dbBean.getBest1v1FinishLeagueName())
                .best1v1FinishTimesAchieved(dbBean.getBest1v1FinishTimesAchieved())
                .bestTeamFinishLeagueName(dbBean.getBestTeamFinishLeagueName())
                .bestTeamFinishTimesAchieved(dbBean.getBestTeamFinishTimesAchieved())
                .currentMMR(dbBean.getCurrentMMR())
                .bestMMR(dbBean.getBestMMR())
                .currentMMR2x2(dbBean.getCurrentMMR2x2())
                .bestMMR2x2(dbBean.getBestMMR2x2())
                .build();

    }
}
