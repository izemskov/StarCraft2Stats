package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;
import ru.develgame.sc2stats.frontend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.service.DialChartService;
import ru.develgame.sc2stats.frontend.service.PlayerService;

import java.util.AbstractMap;
import java.util.Map;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PlayerInfoComposer extends SelectorComposer<Div> {
    @Wire
    private Image mmrCurrent;

    @Wire
    private Image mmrBest;

    @Wire
    private Listbox playerInfoListBox;

    @WireVariable
    private DialChartService dialChartService;

    @WireVariable
    private PlayerService playerService;

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        try {
            PlayerResponseDto player = playerService.fetchPlayerInfo();
            if (player != null) {
                mmrCurrent.setContent(dialChartService.createMMRChart(player.currentMMR(), "Current"));
                mmrBest.setContent(dialChartService.createMMRChart(player.bestMMR(), "Best"));

                ListModelList<Map.Entry<String, String>> playerListBoxModel = new ListModelList<>();
                playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Total games",
                        Integer.toString(player.totalCareerGames())));
                playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best 1v1 league",
                        player.best1v1FinishLeagueName()));
                playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best 1v1 league times",
                        Integer.toString(player.best1v1FinishTimesAchieved())));
                playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best team league",
                        player.bestTeamFinishLeagueName()));
                playerListBoxModel.add(new AbstractMap.SimpleEntry<>("Best team league times",
                        Integer.toString(player.bestTeamFinishTimesAchieved())));
                playerInfoListBox.setModel(playerListBoxModel);
            }
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }
}
