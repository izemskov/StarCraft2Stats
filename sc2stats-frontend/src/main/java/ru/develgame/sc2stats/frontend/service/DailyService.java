package ru.develgame.sc2stats.frontend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zul.Messagebox;
import ru.develgame.sc2stats.frontend.dto.DailyResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {
    private final RestTemplate restTemplate;
    @Value("${sc.backend.baseUrl}")
    private final String backendBaseUrl;

    public List<DailyResponseDto> fetchAll() {
        ResponseEntity<DailyResponseDto[]> response = restTemplate.getForEntity(backendBaseUrl + "/sc2/daily",
                DailyResponseDto[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            // TODO
            Messagebox.show("Error " + response.getStatusCode(), "Error", 0,  Messagebox.ERROR);
            return List.of();
        }

        if (response.getBody() == null) {
            return List.of();
        }

        return List.of(response.getBody());
    }
}
