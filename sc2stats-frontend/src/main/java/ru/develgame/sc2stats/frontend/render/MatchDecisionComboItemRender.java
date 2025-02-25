package ru.develgame.sc2stats.frontend.render;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import ru.develgame.sc2stats.frontend.dto.filter.MatchDecision;

public class MatchDecisionComboItemRender implements ComboitemRenderer<MatchDecision> {
    @Override
    public void render(Comboitem comboitem, MatchDecision matchDecision, int i) throws Exception {
        comboitem.setLabel(matchDecision.getDisplayName());
    }
}
