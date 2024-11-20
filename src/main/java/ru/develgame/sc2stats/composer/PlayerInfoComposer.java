package ru.develgame.sc2stats.composer;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;
import ru.develgame.sc2stats.entity.SC2Player;
import ru.develgame.sc2stats.service.ChartServiceImpl;
import ru.develgame.sc2stats.service.PlayerServiceImpl;
import ru.develgame.sc2stats.service.battlenet.BattleNetUpdateDateServiceImpl;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PlayerInfoComposer extends SelectorComposer<Div> {
    @Wire
    private Image mmrCurrent;

    @Wire
    private Image mmrBest;

    @Wire
    private Label lastUpdateLabel;

    @Wire
    private Listbox playerInfoListBox;

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
            mmrCurrent.setContent(chartServiceImpl.createMMRChart(player.getCurrentMMR(), "Current"));
            mmrBest.setContent(chartServiceImpl.createMMRChart(player.getBestMMR(), "Best"));

            ListModelList<Map.Entry<String, String>> playerListBoxModel = new ListModelList<>();
            playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Total games",
                    Integer.toString(player.getTotalCareerGames())));
            playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best 1v1 league",
                    player.getBest1v1FinishLeagueName()));
            playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best 1v1 league times",
                    Integer.toString(player.getBest1v1FinishTimesAchieved())));
            playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best team league",
                    player.getBestTeamFinishLeagueName()));
            playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best team league times",
                    Integer.toString(player.getBestTeamFinishTimesAchieved())));
            playerInfoListBox.setModel(playerListBoxModel);
        }
    }
}
