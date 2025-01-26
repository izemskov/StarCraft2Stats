package ru.develgame.sc2stats.frontend.service;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

@Slf4j
@Service
public class ChartService {
    public BufferedImage createMMRChart(int mmr, String title) {
        JFreeChart mmrDialChart = createMMRDialChart(mmr, title);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsJPEG(outputStream, mmrDialChart, 300, 300);
            return ImageIO.read(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        }
    }

    private JFreeChart createMMRDialChart(int mmr, String title) {
        DefaultValueDataset dataset = new DefaultValueDataset(mmr);
        JFreeChart jfreechart = createStandardDialChart(title, "MMR", dataset, 0D, 7000D, 1000D, 4);
        DialPlot dialplot = (DialPlot)jfreechart.getPlot();

        StandardDialRange bronzeLeague = new StandardDialRange(0D, 1680D, new Color(205, 127, 50));
        bronzeLeague.setInnerRadius(0.52000000000000002D);
        bronzeLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(bronzeLeague);

        StandardDialRange silverLeague = new StandardDialRange(1681D, 2280D, new Color(192, 192, 192));
        silverLeague.setInnerRadius(0.52000000000000002D);
        silverLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(silverLeague);

        StandardDialRange goldLeague = new StandardDialRange(2281D, 2680D, new Color(255, 215, 0));
        goldLeague.setInnerRadius(0.52000000000000002D);
        goldLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(goldLeague);

        StandardDialRange platinumLeague = new StandardDialRange(2681D, 3120D, new Color(132, 136, 132));
        platinumLeague.setInnerRadius(0.52000000000000002D);
        platinumLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(platinumLeague);

        StandardDialRange diamondLeague = new StandardDialRange(3121D, 4240D, new Color(137, 207, 240));
        diamondLeague.setInnerRadius(0.52000000000000002D);
        diamondLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(diamondLeague);

        StandardDialRange masterLeague = new StandardDialRange(4241D, 4949D, new Color(0, 71, 171));
        masterLeague.setInnerRadius(0.52000000000000002D);
        masterLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(masterLeague);

        StandardDialRange grandMasterLeague = new StandardDialRange(4950D, 7000D, new Color(210, 125, 45));
        grandMasterLeague.setInnerRadius(0.52000000000000002D);
        grandMasterLeague.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(grandMasterLeague);

        dialplot.removePointer(0);
        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();
        pointer.setFillPaint(Color.red);
        dialplot.addPointer(pointer);

        return jfreechart;
    }

    private JFreeChart createStandardDialChart(String title,
                                               String subTitle,
                                               ValueDataset valuedataset,
                                               double from,
                                               double to,
                                               double step,
                                               int segmentation) {
        DialPlot dialplot = new DialPlot();
        dialplot.setDataset(valuedataset);
        dialplot.setDialFrame(new StandardDialFrame());
        dialplot.setBackground(new DialBackground());

        DialTextAnnotation dialtextannotation = new DialTextAnnotation(subTitle);
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);

        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialplot.addLayer(dialvalueindicator);

        StandardDialScale standarddialscale = new StandardDialScale(from, to, -120D, -300D, 10D, 4);
        standarddialscale.setMajorTickIncrement(step);
        standarddialscale.setMinorTickCount(segmentation);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        standarddialscale.setTickLabelFormatter(new DecimalFormat("####"));

        dialplot.addScale(0, standarddialscale);
        dialplot.addPointer(new org.jfree.chart.plot.dial.DialPointer.Pin());
        DialCap dialcap = new DialCap();
        dialplot.setCap(dialcap);

        return new JFreeChart(title, dialplot);
    }
}
