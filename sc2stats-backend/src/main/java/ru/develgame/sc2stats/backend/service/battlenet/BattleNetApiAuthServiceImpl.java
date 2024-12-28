package ru.develgame.sc2stats.backend.service.battlenet;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.develgame.sc2stats.backend.configuration.battlenetapi.BattleNetProperties;
import ru.develgame.sc2stats.backend.dto.battlenet.BattleNetApiAccessTokenResponseDto;

@Service
@RequiredArgsConstructor
public class BattleNetApiAuthServiceImpl implements BattleNetApiAuthService {
    private final BattleNetProperties battleNetProperties;
    private final RestTemplate restTemplate;

    private String authUrl = "https://oauth.battle.net/token";

    @Override
    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String plainCreds = battleNetProperties.clientId() + ":" + battleNetProperties.clientSecret();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BattleNetApiAccessTokenResponseDto> response = restTemplate.postForEntity(authUrl,
                request, BattleNetApiAccessTokenResponseDto.class);

        return response.getBody().accessToken();
    }
}
