package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;

import static ru.develgame.sc2stats.frontend.composer.MapComposer.*;

public class MapsFiltersComposer  extends SelectorComposer<Window> {
    @Wire
    private Window mapsFilters;

    @Wire
    private Combobox matchTypeCombobox;

    private ListModelList<MatchType> matchTypesModel;

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);

        Session session = Sessions.getCurrent();

        MatchType matchType = (MatchType) session.getAttribute(MAP_FILTER_MATCH_TYPE);

        matchTypesModel = new ListModelList<>(MatchType.values());
        matchTypesModel.addToSelection(matchType == null ? MatchType.TYPE_NONE : matchType);
        matchTypeCombobox.setModel(matchTypesModel);
    }

    @Listen("onClick = #okButton")
    public void okButtonOnClick() {
        Session session = Sessions.getCurrent();
        session.setAttribute(MAP_FILTER_MATCH_TYPE, matchTypesModel.getSelection().iterator().next());

        EventQueue<Event> eventQueue = EventQueues.lookup(MAP_EVENT_QUEUE_NAME, EventQueues.DESKTOP, true);
        eventQueue.publish(new Event(MAP_EVENT_FILTER, null, null));

        mapsFilters.detach();
    }

    @Listen("onClick = #cancelButton")
    public void cancelOnClick() {
        mapsFilters.detach();
    }
}
