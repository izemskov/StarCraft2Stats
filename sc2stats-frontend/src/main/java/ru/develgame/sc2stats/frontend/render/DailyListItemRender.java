package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import ru.develgame.sc2stats.frontend.dto.DailyResponseDto;

public class DailyListItemRender implements ListitemRenderer<DailyResponseDto> {
    @Override
    public void render(Listitem listitem, DailyResponseDto daily, int i) throws Exception {
        listitem.appendChild(new Listcell(daily.date()));
        listitem.appendChild(new Listcell(daily.type()));
        listitem.appendChild(new Listcell(Integer.toString(daily.wins())));
        listitem.appendChild(new Listcell(Integer.toString(daily.losses())));
    }
}
