package ru.develgame.sc2stats.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SC2PLAYER")
public class Player {
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
