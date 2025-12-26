package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET="TEST_SECRET_KEY_123456";
    private final long EXP=86400000;

    public String generateToken(Map<String,Object> claims, String user){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, String user){
        return getUsername(token).equals(user) && !isExpired(token);
    }

    public long getExpirationMillis(){ return EXP; }

    private boolean isExpired(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
