package com.exspensetracker.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // 512 bit (64 bayt) uzunluğunda açar: 64+ xarakterdən ibarət string
    private static final String SECRET_STRING = "BuCoxUzunVeGucluBirSecretKeyOlmalidir12345678901234567890123456789012345678901234567890";

    private final SecretKey secretKey;

    public JwtUtil() {
        // String-i byte[]-ə çevirərək SecretKey yaradılır
        this.secretKey = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userId) {
        long EXPIRATION_TIME = 86400000; // 1 gün millisaniyə ilə
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) // HS512 üçün SecretKey istifadə olunur
                .compact();
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
