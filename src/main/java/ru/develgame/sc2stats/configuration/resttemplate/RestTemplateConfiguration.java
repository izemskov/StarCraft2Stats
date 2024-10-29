package ru.develgame.sc2stats.configuration.resttemplate;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        var requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom()
                        .setDefaultRequestConfig(RequestConfig.custom()
                                .setConnectTimeout(restTemplateProperties.connectTimeout(), TimeUnit.MILLISECONDS)
                                .setResponseTimeout(restTemplateProperties.responseTimeout(), TimeUnit.MILLISECONDS)
                                .build())
                        .build());
        return restTemplateBuilder
                .requestFactory(() -> requestFactory)
                .build();
    }
}
