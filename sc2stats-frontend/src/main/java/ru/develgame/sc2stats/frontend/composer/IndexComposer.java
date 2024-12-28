package ru.develgame.sc2stats.frontend.composer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import ru.develgame.sc2stats.frontend.dto.DailyResponseDto;
import ru.develgame.sc2stats.frontend.dto.MatchResponseDto;
import ru.develgame.sc2stats.frontend.service.DailyService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IndexComposer extends SelectorComposer<Div> {
    @Wire
    private Listbox matchesList;

    @Wire
    private Listbox dailyList;

//    @WireVariable
//    private MatchService matchService;

    @WireVariable
    private DailyService dailyService;


    private ListModelList<MatchResponseDto> matchesDataModel = new ListModelList<>();

    private ListModelList<DailyResponseDto> dailyDataModel = new ListModelList<>();

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

//        matchesDataModel = new ListModelList<>(matchServiceImpl.fetchAllMatchesSortedByDateDesc());
//        matchesList.setModel(matchesDataModel);
//
        dailyDataModel = new ListModelList<>(dailyService.fetchAll());
        dailyList.setModel(dailyDataModel);
    }
}
