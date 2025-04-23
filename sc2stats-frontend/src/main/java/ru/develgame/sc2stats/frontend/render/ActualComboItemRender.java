package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import ru.develgame.sc2stats.frontend.dto.filter.Actual;

public class ActualComboItemRender implements ComboitemRenderer<Actual> {
    @Override
    public void render(Comboitem comboitem, Actual actual, int i) throws Exception {
        comboitem.setLabel(actual.getDisplayName());
    }
}
