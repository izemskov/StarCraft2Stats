package ru.develgame.sc2stats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SC2Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int totalCareerGames;

    private String best1v1FinishLeagueName;

    private int best1v1FinishTimesAchieved;

    private String bestTeamFinishLeagueName;

    private int bestTeamFinishTimesAchieved;

    private int currentMMR;

    private int bestMMR;
}
