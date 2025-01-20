package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.dto.DailyResponseDto;
import ru.develgame.sc2stats.frontend.dto.MatchResponseDto;
import ru.develgame.sc2stats.frontend.service.DailyService;
import ru.develgame.sc2stats.frontend.service.MatchService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IndexComposer extends SelectorComposer<Div> {
    @Wire
    private Listbox matchesList;

    @Wire
    private Listbox dailyList;

    @WireVariable
    private MatchService matchService;

    @WireVariable
    private DailyService dailyService;

    private ListModelList<MatchResponseDto> matchesDataModel = new ListModelList<>();

    private ListModelList<DailyResponseDto> dailyDataModel = new ListModelList<>();

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        try {
            matchesDataModel = new ListModelList<>(matchService.fetchAll());
            matchesList.setModel(matchesDataModel);
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }

        try {
            dailyDataModel = new ListModelList<>(dailyService.fetchAll());
            dailyList.setModel(dailyDataModel);
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }
}
