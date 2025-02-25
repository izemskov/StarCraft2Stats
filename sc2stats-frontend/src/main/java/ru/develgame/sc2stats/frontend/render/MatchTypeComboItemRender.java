package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import ru.develgame.sc2stats.frontend.dto.filter.MatchType;

public class MatchTypeComboItemRender implements ComboitemRenderer<MatchType> {
    @Override
    public void render(Comboitem comboitem, MatchType matchType, int i) throws Exception {
        comboitem.setLabel(matchType.getDisplayName());
    }
}
