package ru.develgame.sc2stats.backend.bean;

import lombok.Builder;
import ru.develgame.sc2stats.backend.entity.Match;

import java.util.List;

@Builder
public record MatchesPage(long total,
                          List<Match> matches) {
}
