package ru.develgame.sc2stats.battlenet.oauth.composer;

import ru.develgame.sc2stats.battlenet.oauth.configuration.battlenet.BattleNetPropertiesProxy;
import ru.develgame.sc2stats.battlenet.oauth.dto.BattleNetUserInfoDto;
import ru.develgame.sc2stats.battlenet.oauth.dto.BattleNetAuthResponse;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.Map;

import static org.keycloak.OAuth2Constants.*;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BattleNetComposer extends SelectorComposer<Div> {
    @Wire
    private Label authCodeLabel;
    @Wire
    private Textbox accessTokenTextbox;
    @Wire
    private Listbox userInfoList;

    @WireVariable
    private RestTemplate restTemplate;
    @WireVariable
    private BattleNetPropertiesProxy battleNetPropertiesProxy;

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);
        authCodeLabel.setValue(Executions.getCurrent().getParameter("code"));
    }

    @Listen("onClick = #accessTokenExchangeButton")
    public void accessTokenExchangeButtonOnClick() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(GRANT_TYPE, AUTHORIZATION_CODE);
        map.add(CLIENT_ID, battleNetPropertiesProxy.getClientId());
        map.add(CLIENT_SECRET, battleNetPropertiesProxy.getClientSecret());
        map.add(REDIRECT_URI, "http://localhost:8084/battlenet");
        map.add(CODE, authCodeLabel.getValue());
        map.add("region", "us");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BattleNetAuthResponse> response = restTemplate.postForEntity("https://oauth.battle.net/oauth/token",
                request, BattleNetAuthResponse.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            Messagebox.show("Error " + response.getStatusCode(), "Error", 0, Messagebox.ERROR);
            return;
        }

        accessTokenTextbox.setValue(response.getBody().getAccessToken());
    }

    @Listen("onClick = #playerInfoRequestButton")
    public void playerInfoRequestButtonOnClick() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + accessTokenTextbox.getValue());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("region", "us");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BattleNetUserInfoDto> response = restTemplate.exchange(
                "https://oauth.battle.net/oauth/userinfo", HttpMethod.GET, request, BattleNetUserInfoDto.class);

        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("sub", response.getBody().sub());
        userInfoMap.put("id", response.getBody().id());
        userInfoMap.put("battletag", response.getBody().battletag());

        ListModelList<Map.Entry<String, String>> userInfoModel = new ListModelList<>(userInfoMap.entrySet());
        userInfoList.setModel(userInfoModel);
    }

    @Listen("onClick = #redirectButton")
    public void redirectButtonOnClick() {
        Executions.sendRedirect("http://localhost:8084");
    }
}
