package ru.develgame.sc2stats.backend.service.battlenet;

import java.util.Date;

public interface UpdateDateService {
    Date getLastUpdateDate();

    void updateLastUpdateDate();
}
