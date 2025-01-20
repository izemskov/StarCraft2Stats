package ru.develgame.sc2stats.frontend.composer;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import ru.develgame.sc2stats.frontend.exception.GetDataException;
import ru.develgame.sc2stats.frontend.service.UpdateDateService;

import java.text.SimpleDateFormat;
import java.util.Date;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainMenuComposer extends SelectorComposer<Div> {
    @Wire
    private Label lastUpdateLabel;

    @Wire
    private Label titleLabel;

    @WireVariable
    private UpdateDateService updateDateService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    private final String DATE_UNKNOWN = "unknown";

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
        if (requestPath.equals("/player.zul")) {
            titleLabel.setValue("StarCraft 2 Player info");
        } else if (requestPath.endsWith("/team.zul")) {
            titleLabel.setValue("StarCraft 2 Team info");
        }

        try {
            Date lastUpdateDate = updateDateService.fetchLastUpdateDate();
            lastUpdateLabel.setValue(String.format("Last update: %s",
                    lastUpdateDate == null
                            ? DATE_UNKNOWN
                            : dateFormat.format(lastUpdateDate)));
        } catch (GetDataException ex) {
            lastUpdateLabel.setValue(DATE_UNKNOWN);
            Messagebox.show(ex.getMessage(), "Error", 0,  Messagebox.ERROR);
        }
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
