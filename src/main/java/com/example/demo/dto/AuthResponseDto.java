package com.example.demo.dto;

import java.time.Instant;

public class AuthResponseDto {

    private String token;
    private Instant expiresAt;

    public AuthResponseDto(String token, Instant expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() { return token; }
    public Instant getExpiresAt() { return expiresAt; }
}
