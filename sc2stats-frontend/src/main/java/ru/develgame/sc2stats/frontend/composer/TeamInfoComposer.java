package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import ru.develgame.sc2stats.frontend.dto.PlayerResponseDto;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.service.ChartService;
import ru.develgame.sc2stats.frontend.service.PlayerService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TeamInfoComposer extends SelectorComposer<Div> {
    @Wire
    private Image mmrCurrent2x2;

    @Wire
    private Image mmrBest2x2;

    @Wire
    private Image mmrCurrent3x3;

    @Wire
    private Image mmrBest3x3;

    @WireVariable
    private ChartService chartService;

    @WireVariable
    private PlayerService playerService;

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        try {
            PlayerResponseDto player = playerService.fetchPlayerInfo();
            if (player != null) {
                mmrCurrent2x2.setContent(chartService.createMMRChart(player.currentMMR2x2(), "Current 2x2"));
                mmrBest2x2.setContent(chartService.createMMRChart(player.bestMMR2x2(), "Best 2x2"));
                mmrCurrent3x3.setContent(chartService.createMMRChart(player.currentMMR3x3(), "Current 3x3"));
                mmrBest3x3.setContent(chartService.createMMRChart(player.bestMMR3x3(), "Best 3x3"));
            }
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }
}
