package com.example.demo.dto;

import java.time.Instant;

public class AuthResponseDto {
    private String token;
    private Instant expiresAt;

    public AuthResponseDto() {}
    public AuthResponseDto(String token, Instant expiresAt) {
        this.token = token; this.expiresAt = expiresAt;
    }

    // getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
    