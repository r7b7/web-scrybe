package com.r7b7.webscrybe.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class TokenStorage {
    private String accessToken;
    private Instant expiresAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setTokens(String accessToken, Instant expiresAt) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public boolean isTokenExpired() {
        return Instant.now().isAfter(expiresAt.minus(5, ChronoUnit.MINUTES)); // 5 minutes before expiration
    }
}
