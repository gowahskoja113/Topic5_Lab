package com.example.lab1.service;

import com.example.lab1.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey base64Secret;
    private final Long expMinutes;

    public JwtService(
            @Value("${jwt.secret}") String base64Secret,
            @Value("${jwt.exp-minutes:60}") Long expMinutes) {
        this.base64Secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expMinutes = expMinutes;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
                .signWith(base64Secret)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(base64Secret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}