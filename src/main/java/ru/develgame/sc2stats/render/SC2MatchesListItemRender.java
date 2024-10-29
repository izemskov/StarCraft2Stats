package ru.develgame.sc2stats.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import ru.develgame.sc2stats.entity.SC2Match;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SC2MatchesListItemRender implements ListitemRenderer<SC2Match> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mmß");

    @Override
    public void render(Listitem listitem, SC2Match sc2Match, int i) throws Exception {
        listitem.appendChild(new Listcell(sc2Match.getMap()));
        listitem.appendChild(new Listcell(sc2Match.getType()));
        listitem.appendChild(new Listcell(sc2Match.getDecision()));
        listitem.appendChild(new Listcell(dateFormat.format(new Date(sc2Match.getDate() * 1000L))));
    }
}