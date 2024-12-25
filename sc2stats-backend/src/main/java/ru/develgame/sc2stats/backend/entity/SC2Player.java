package ru.develgame.sc2stats.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("0")
    private int currentMMR;

    @ColumnDefault("0")
    private int bestMMR;

    @ColumnDefault("0")
    private int currentMMR2x2;

    @ColumnDefault("0")
    private int bestMMR2x2;
}
