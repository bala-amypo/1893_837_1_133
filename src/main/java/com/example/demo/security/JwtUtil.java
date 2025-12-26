package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JwtUtil {

    // Very simple token generator for tests: UUID + email
    public String generateToken(UserAccount user) {
        return UUID.randomUUID().toString() + "|" + user.getEmail();
    }

    public boolean validateToken(String token) {
        return token != null && token.contains("|");
    }

    public String extractEmail(String token) {
        if (token == null) return null;
        String[] parts = token.split("\\|");
        return parts.length > 1 ? parts[1] : null;
    }

    public Instant getExpiry(String token) {
        return Instant.now().plusSeconds(3600);
    }
}
