package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.dto.DailyResponseDto;
import ru.develgame.sc2stats.frontend.dto.MatchResponseDto;
import ru.develgame.sc2stats.frontend.service.DailyService;
import ru.develgame.sc2stats.frontend.service.MatchService;

import java.util.HashMap;
import java.util.Map;

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

    public static final String EVENT_QUEUE_NAME = "SC2StatsEvents";
    public static final String EVENT_FILTER = "EventFilter";
    public static final String FILTER_MATCH_TYPE = "FilterMatchType";
    public static final String FILTER_MATCH_DECISION = "FilterMatchDecision";

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        loadDailies();
        loadMatches();

        EventQueue<Event> eventQueue = EventQueues.lookup(EVENT_QUEUE_NAME, EventQueues.DESKTOP, true);
        eventQueue.subscribe(event -> {
            if (EVENT_FILTER.equals(event.getName())) {
                loadMatches();
            }
        });
    }

    private void loadDailies() {
        try {
            dailyDataModel = new ListModelList<>(dailyService.fetchAll());
            dailyList.setModel(dailyDataModel);
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }

    private void loadMatches() {
        try {
            Session session = Sessions.getCurrent();
            System.out.println(session.getAttribute(FILTER_MATCH_TYPE));
            System.out.println(session.getAttribute(FILTER_MATCH_DECISION));

            matchesDataModel = new ListModelList<>(matchService.fetchAll());
            matchesList.setModel(matchesDataModel);
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }

    @Listen("onClick = #matchesFiltersButton")
    public void matchesFiltersButtonOnClick() {
        Window window = (Window) Executions.createComponents(
                "~./widgets/matchesFilters.zul", null, null);
        window.doModal();
    }
}
