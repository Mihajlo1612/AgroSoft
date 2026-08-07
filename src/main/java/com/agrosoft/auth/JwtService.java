package com.agrosoft.auth;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000);
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(expirationDate)
            .signWith(getSecretKey())
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
