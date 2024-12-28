package ru.develgame.sc2stats.backend.service.battlenet;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UpdateDateServiceImpl implements UpdateDateService {
    private AtomicReference<Date> lastUpdateDate = new AtomicReference<>();

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate.get();
    }

    @Override
    public void updateLastUpdateDate() {
        lastUpdateDate.set(new Date());
    }
}
