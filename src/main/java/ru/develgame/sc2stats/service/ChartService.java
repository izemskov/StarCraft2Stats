package ru.develgame.sc2stats.service;

import java.awt.image.BufferedImage;

public interface ChartService {
    BufferedImage createMMRChart(int mmr, String title);
}
