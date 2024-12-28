package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import java.text.SimpleDateFormat;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainMenuComposer extends SelectorComposer<Div> {
    @Wire
    private Label lastUpdateLabel;

    @Wire
    private Label titleLabel;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
        if (requestPath.equals("/player.zul")) {
            titleLabel.setValue("StarCraft 2 Player info");
        } else if (requestPath.endsWith("/team.zul")) {
            titleLabel.setValue("StarCraft 2 Team info");
        }

//        lastUpdateLabel.setValue(String.format("Last update: %s",
//                battleNetUpdateDateServiceImpl.getLastUpdateDate() == null
//                        ? "unknown"
//                        : dateFormat.format(battleNetUpdateDateServiceImpl.getLastUpdateDate())));
    }

    @Listen("onClick = #menuStatistics")
    public void menuStatisticsOnClick() {
        Execution exec = Executions.getCurrent();
        exec.sendRedirect("/");
    }

    @Listen("onClick = #menuPlayer")
    public void menuPlayerOnClick() {
        Execution exec = Executions.getCurrent();
        exec.sendRedirect("/player");
    }

    @Listen("onClick = #menuTeam")
    public void menuTeamOnClick() {
        Execution exec = Executions.getCurrent();
        exec.sendRedirect("/team");
    }
}
