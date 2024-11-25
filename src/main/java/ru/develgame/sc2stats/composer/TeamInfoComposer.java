package ru.develgame.sc2stats.composer;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import ru.develgame.sc2stats.entity.SC2Player;
import ru.develgame.sc2stats.service.ChartServiceImpl;
import ru.develgame.sc2stats.service.PlayerServiceImpl;
import ru.develgame.sc2stats.service.battlenet.BattleNetUpdateDateServiceImpl;

import java.text.SimpleDateFormat;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TeamInfoComposer extends SelectorComposer<Div> {
    @Wire
    private Image mmrCurrent2x2;

    @Wire
    private Image mmrBest2x2;

    @Wire
    private Label lastUpdateLabel;

    @WireVariable
    private ChartServiceImpl chartServiceImpl;

    @WireVariable
    private PlayerServiceImpl playerServiceImpl;

    @WireVariable
    private BattleNetUpdateDateServiceImpl battleNetUpdateDateServiceImpl;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        lastUpdateLabel.setValue(String.format("Last update: %s",
                battleNetUpdateDateServiceImpl.getLastUpdateDate() == null
                        ? "unknown"
                        : dateFormat.format(battleNetUpdateDateServiceImpl.getLastUpdateDate())));

        SC2Player player = playerServiceImpl.getCurrentPlayer();
        if (player != null) {
            mmrCurrent2x2.setContent(chartServiceImpl.createMMRChart(player.getCurrentMMR2x2(), "Current 2x2"));
            mmrBest2x2.setContent(chartServiceImpl.createMMRChart(player.getBestMMR2x2(), "Best 2x2"));
        }
    }
}
