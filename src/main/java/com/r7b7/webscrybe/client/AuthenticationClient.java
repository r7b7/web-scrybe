package com.r7b7.webscrybe.client;

import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.r7b7.webscrybe.config.AppConfigProperties;
import com.r7b7.webscrybe.config.Constants;
import com.r7b7.webscrybe.config.TokenStorage;
import com.r7b7.webscrybe.entity.TokenResponse;

@Component
public class AuthenticationClient {
    private final RestTemplate restTemplate;
    private final TokenStorage tokenStorage;
    private final AppConfigProperties appConfigProperties;

    @Value("${REDDIT_CLIENT_ID:no-client-id}")
    private String clientId;

    @Value("${REDDIT_CLIENT_SECRET:no-client-secret}")
    private String clientSecret;

    public AuthenticationClient(RestTemplate restTemplate, TokenStorage tokenStorage,AppConfigProperties appConfigProperties) {
        this.restTemplate = restTemplate;
        this.tokenStorage = tokenStorage;
        this.appConfigProperties = appConfigProperties;
    }

    public TokenStorage getToken() {
        if(null != tokenStorage.getAccessToken() && !tokenStorage.isTokenExpired()){
            return tokenStorage;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.CONTENT_TYPE, Constants.MULTIPART_FORM_DATA);
        headers.set(Constants.AUTHORIZATION, getAuthHeader());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(appConfigProperties.getRedditAuthUrl(),
                HttpMethod.POST, requestEntity, TokenResponse.class);
        Instant expiryTime = Instant.now().plusSeconds(response.getBody().getExpiresIn());
        tokenStorage.setTokens(response.getBody().getAccessToken(), expiryTime);
        return tokenStorage;

    }

    private String getAuthHeader() {
        String credentials = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

}
