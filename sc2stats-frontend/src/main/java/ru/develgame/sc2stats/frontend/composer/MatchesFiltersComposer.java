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
import ru.develgame.sc2stats.frontend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;

import static ru.develgame.sc2stats.frontend.composer.IndexComposer.*;

public class MatchesFiltersComposer extends SelectorComposer<Window> {
    @Wire
    private Window matchesFilters;

    @Wire
    private Combobox matchTypeCombobox;

    @Wire
    private Combobox matchDecisionCombobox;

    private ListModelList<MatchType> matchTypesModel;

    private ListModelList<MatchDecision> matchDecisionModel;

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);

        Session session = Sessions.getCurrent();

        MatchType matchType = (MatchType) session.getAttribute(FILTER_MATCH_TYPE);
        MatchDecision matchDecision = (MatchDecision) session.getAttribute(FILTER_MATCH_DECISION);

        matchTypesModel = new ListModelList<>(MatchType.values());
        matchTypesModel.addToSelection(matchType == null ? MatchType.TYPE_NONE : matchType);
        matchTypeCombobox.setModel(matchTypesModel);

        matchDecisionModel = new ListModelList<>(MatchDecision.values());
        matchDecisionModel.addToSelection(matchDecision == null ? MatchDecision.NONE : matchDecision);
        matchDecisionCombobox.setModel(matchDecisionModel);
    }

    @Listen("onClick = #okButton")
    public void okButtonOnClick() {
        Session session = Sessions.getCurrent();
        session.setAttribute(FILTER_MATCH_TYPE, matchTypesModel.getSelection().iterator().next());
        session.setAttribute(FILTER_MATCH_DECISION, matchDecisionModel.getSelection().iterator().next());

        EventQueue<Event> eventQueue = EventQueues.lookup(EVENT_QUEUE_NAME, EventQueues.DESKTOP, true);
        eventQueue.publish(new Event(EVENT_FILTER, null, null));

        matchesFilters.detach();
    }

    @Listen("onClick = #cancelButton")
    public void cancelOnClick() {
        matchesFilters.detach();
    }
}
