package com.masterproject.musigame.configuration.firebase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeniusWebClientConfiguration {
    @Value("${musigame.genius.client-access-token}")
    private String clientAccessToken;

    @Bean
    public WebClient.Builder geniusWebClientBuilder() {
        return WebClient.builder().defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + clientAccessToken);
    }

    @Bean
    public WebClient geniusWebClient() {
        return geniusWebClientBuilder().build();
    }


}
