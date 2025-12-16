package com.project.mymemory.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    // spring.jwt-secret=your-secret-key
    @SuppressWarnings("unused")
    @Value("${spring.jwt-secret}")
    private String jwtSecret;

    // spring.jwt-expire=7d or 24h or 60m
    @SuppressWarnings("unused")
    @Value("${spring.jwt-expire}")
    private String expirationTime;

    // Convert the secret string into a SecretKey used to sign JWT
    private SecretKey getSignKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // jjwt function
    }

    // Convert "7d", "1h", "30m" into milliseconds
    private long getExpirationMs() {
        return Duration.parse("P" + expirationTime.toUpperCase()).toMillis();
    }

    // Pass userId, email, and optional role
    public String generateToken(String userId, String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);

        if (role != null && !role.isBlank()) {
            claims.put("role", role);
        }

        return Jwts.builder()
                .claims(claims)                           // Add custom data
                .subject("sub")
                // Expire time
                .expiration(new Date(System.currentTimeMillis() + getExpirationMs()))
                // Sign the token using your secret key
                .signWith(getSignKey(), Jwts.SIG.HS512)
                // Final token string
                .compact();
    }
}
