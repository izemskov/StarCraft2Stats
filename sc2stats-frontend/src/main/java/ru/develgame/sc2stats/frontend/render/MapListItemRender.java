package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import ru.develgame.sc2stats.frontend.dto.MapResponseDto;

public class MapListItemRender implements ListitemRenderer<MapResponseDto> {
    @Override
    public void render(Listitem listitem, MapResponseDto map, int i) throws Exception {
        listitem.appendChild(new Listcell(map.name()));
        listitem.appendChild(new Listcell(map.type()));
        listitem.appendChild(new Listcell(Integer.toString(map.wins())));
        listitem.appendChild(new Listcell(Integer.toString(map.losses())));
        listitem.appendChild(new Listcell(Integer.toString(map.winRate())));
    }
}
