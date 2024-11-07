package ru.develgame.sc2stats.service.battlenet;

import java.util.Date;

public interface BattleNetUpdateDateService {
    Date getLastUpdateDate();

    void updateLastUpdateDate();
}
