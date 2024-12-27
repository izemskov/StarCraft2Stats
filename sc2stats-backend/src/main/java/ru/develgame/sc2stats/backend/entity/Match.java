package ru.develgame.sc2stats.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SC2MATCH")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String map;
    private String type;
    private String decision;
    private String speed;
    private long date;
}
