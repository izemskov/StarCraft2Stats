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
import ru.develgame.sc2stats.frontend.dto.MapResponseDto;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.service.MapService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MapComposer extends SelectorComposer<Div> {
    @Wire
    private Listbox mapList;

    @WireVariable
    private MapService mapService;

    private ListModelList<MapResponseDto> mapsDataModel = new ListModelList<>();


    public static final String MAP_EVENT_QUEUE_NAME = "SC2StatsMapsEvents";
    public static final String MAP_EVENT_FILTER = "MapsEventFilter";
    public static final String MAP_FILTER_MATCH_TYPE = "MapFilterMatchType";

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        loadMaps();

        EventQueue<Event> eventQueue = EventQueues.lookup(MAP_EVENT_QUEUE_NAME, EventQueues.DESKTOP, true);
        eventQueue.subscribe(event -> {
            if (MAP_EVENT_FILTER.equals(event.getName())) {
                loadMaps();
            }
        });
    }

    private void loadMaps() {
        try {
            Session session = Sessions.getCurrent();

            mapsDataModel = new ListModelList<>(mapService.fetchAll(
                    (MatchType) session.getAttribute(MAP_FILTER_MATCH_TYPE)));
            mapList.setModel(mapsDataModel);
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }

    @Listen("onClick = #mapFiltersButton")
    public void mapFiltersButtonOnClick() {
        Window window = (Window) Executions.createComponents(
                "~./widgets/mapFilters.zul", null, null);
        window.doModal();
    }
}