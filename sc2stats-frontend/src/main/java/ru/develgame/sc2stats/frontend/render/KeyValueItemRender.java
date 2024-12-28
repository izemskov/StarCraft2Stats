package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import java.util.Map;

public class KeyValueItemRender implements ListitemRenderer<Map.Entry<String, String>> {
    @Override
    public void render(Listitem listitem, Map.Entry<String, String> value, int i) throws Exception {
        listitem.appendChild(new Listcell(value.getKey()));
        listitem.appendChild(new Listcell(value.getValue()));
    }
}
