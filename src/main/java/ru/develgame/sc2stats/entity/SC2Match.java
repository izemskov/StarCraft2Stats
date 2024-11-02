package ru.develgame.sc2stats.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SC2Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String map;
    private String type;
    private String decision;
    private String speed;
    private long date;
}
