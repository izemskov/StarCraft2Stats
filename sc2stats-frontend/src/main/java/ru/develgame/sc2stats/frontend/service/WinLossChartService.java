package ru.develgame.sc2stats.frontend.service;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.springframework.stereotype.Service;
import ru.develgame.sc2stats.frontend.dto.MapResponseDto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class WinLossChartService {
    public BufferedImage createWinLossChart(List<MapResponseDto> maps) {
        JFreeChart mmrDialChart = createChart(createDataset(maps));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsJPEG(outputStream, mmrDialChart, 800, 600);
            return ImageIO.read(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        }
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Win/Loss",
                "Maps",
                "Decisions",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );
        return chart;
    }

    private CategoryDataset createDataset(List<MapResponseDto> maps) {
        DefaultKeyedValues2DDataset data = new DefaultKeyedValues2DDataset();
        maps.forEach(t -> data.addValue(-1 * t.losses(), "Loss", "%s %s".formatted(t.name(), t.type())));
        maps.forEach(t -> data.addValue(t.wins(), "Win", "%s %s".formatted(t.name(), t.type())));
        return data;
    }
}
