package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import ru.develgame.sc2stats.frontend.dto.MatchResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchesListItemRender implements ListitemRenderer<MatchResponseDto> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

    @Override
    public void render(Listitem listitem, MatchResponseDto match, int i) throws Exception {
        listitem.appendChild(new Listcell(match.map()));
        listitem.appendChild(new Listcell(match.type()));
        listitem.appendChild(new Listcell(match.decision()));
        listitem.appendChild(new Listcell(dateFormat.format(new Date(match.date() * 1000L))));
    }
}