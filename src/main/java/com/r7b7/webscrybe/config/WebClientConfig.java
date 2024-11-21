package com.r7b7.webscrybe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.r7b7.webscrybe.client.AuthenticationClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    private final AuthenticationClient authClient;

    @Value("${USER_AGENT:no-user-agent}")
    private String userAgent;

    public WebClientConfig(AuthenticationClient authClient) {
        this.authClient = authClient;
    }

    @Bean
    public WebClient redditWebClient() {
        return WebClient.builder()
                .defaultHeaders(headers -> {
                    headers.set(HttpHeaders.USER_AGENT, userAgent); 
                    headers.set(HttpHeaders.CONTENT_LENGTH, "0"); 
                    headers.set(HttpHeaders.ACCEPT, "application/json");
                })
                .filter(authInterceptor())
                .build();
    }

    private ExchangeFilterFunction authInterceptor() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            ClientRequest newRequest = ClientRequest.from(clientRequest)
                    .header("Authorization", "Bearer " + authClient.getToken().getAccessToken())
                    .build();
            return Mono.just(newRequest);
        });
    }
}
