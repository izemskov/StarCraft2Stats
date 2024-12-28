package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import ru.develgame.sc2stats.frontend.service.ChartServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TeamInfoComposer extends SelectorComposer<Div> {
    @Wire
    private Image mmrCurrent2x2;

    @Wire
    private Image mmrBest2x2;

    @WireVariable
    private ChartServiceImpl chartServiceImpl;

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

//        SC2Player player = playerServiceImpl.getCurrentPlayer();
//        if (player != null) {
//            mmrCurrent2x2.setContent(chartServiceImpl.createMMRChart(player.getCurrentMMR2x2(), "Current 2x2"));
//            mmrBest2x2.setContent(chartServiceImpl.createMMRChart(player.getBestMMR2x2(), "Best 2x2"));
//        }
    }
}
