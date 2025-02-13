package ru.develgame.sc2stats.battlenet.oauth.composer;

import ru.develgame.sc2stats.battlenet.oauth.configuration.battlenet.BattleNetPropertiesProxy;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;

import static ru.develgame.sc2stats.battlenet.oauth.composer.BattleNetComposer.BATTLE_NET_ROOT_URL;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IndexComposer extends SelectorComposer<Div> {
    @WireVariable
    private BattleNetPropertiesProxy battleNetPropertiesProxy;

    @Listen("onClick = #battleNetFlowBtn")
    public void battleNetFlowBtnOnClick() {
        Executions.sendRedirect(BATTLE_NET_ROOT_URL + "/authorize"
                + "?response_type=code"
                + "&region=us"
                + "&scope=sc2.profile"
                + "&client_id=" + battleNetPropertiesProxy.getClientId()
                + "&redirect_uri=http://localhost:8084/battlenet");
    }
}
