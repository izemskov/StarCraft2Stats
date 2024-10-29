package ru.develgame.sc2stats.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import ru.develgame.sc2stats.entity.SC2Daily;

public class SC2DailyListItemRender implements ListitemRenderer<SC2Daily> {
    @Override
    public void render(Listitem listitem, SC2Daily sc2Daily, int i) throws Exception {
        listitem.appendChild(new Listcell(sc2Daily.getDate()));
        listitem.appendChild(new Listcell(sc2Daily.getType()));
        listitem.appendChild(new Listcell(Integer.toString(sc2Daily.getWins())));
        listitem.appendChild(new Listcell(Integer.toString(sc2Daily.getLosses())));
    }
}
