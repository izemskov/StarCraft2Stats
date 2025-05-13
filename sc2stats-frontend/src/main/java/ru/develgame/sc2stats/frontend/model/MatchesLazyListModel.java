package ru.develgame.sc2stats.frontend.model;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Messagebox;
import ru.develgame.sc2stats.frontend.dto.MatchResponseDto;
import ru.develgame.sc2stats.frontend.dto.MatchesResponseDto;
import ru.develgame.sc2stats.frontend.dto.filter.MatchDecision;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.service.MatchService;

import java.util.ArrayList;

import static ru.develgame.sc2stats.frontend.composer.IndexComposer.FILTER_MATCH_DECISION;
import static ru.develgame.sc2stats.frontend.composer.IndexComposer.FILTER_MATCH_TYPE;

public class MatchesLazyListModel extends AbstractListModel<MatchResponseDto> {
    private final MatchService matchService;
    private ArrayList<MatchResponseDto> matches = new ArrayList<>();
    private int size;

    public MatchesLazyListModel(MatchService matchService) {
        this.matchService = matchService;
        setPageSize(25);

        try {
            Session session = Sessions.getCurrent();
            MatchesResponseDto matchesResponseDto = matchService.fetchAll((MatchType) session.getAttribute(FILTER_MATCH_TYPE),
                    (MatchDecision) session.getAttribute(FILTER_MATCH_DECISION),
                    0,
                    getPageSize());
            size = (int) matchesResponseDto.total();

            matches.addAll(matchesResponseDto.matches());
            for (int i = getPageSize(); i < size; i++) {
                matches.add(null);
            }
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
    }

    @Override
    public MatchResponseDto getElementAt(int i) {
        MatchResponseDto matchResponseDto = matches.get(i);
        if (matchResponseDto != null) {
            return matchResponseDto;
        }

        try {
            int page = i / getPageSize();
            Session session = Sessions.getCurrent();
            MatchesResponseDto matchesResponseDto = matchService.fetchAll((MatchType) session.getAttribute(FILTER_MATCH_TYPE),
                    (MatchDecision) session.getAttribute(FILTER_MATCH_DECISION),
                    page,
                    getPageSize());
            for (int j = 0; j < matchesResponseDto.matches().size(); j++) {
                matches.set(page * getPageSize() + j, matchesResponseDto.matches().get(j));
            }

            return matches.get(i);
        } catch (GetDataException ex) {
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
            return null;
        }
    }

    @Override
    public int getSize() {
        return size;
    }
}
