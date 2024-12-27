package ru.develgame.sc2stats.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SC2DAILY")
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private int wins;
    private int losses;
    private String date;
    private long timestamp;
}
