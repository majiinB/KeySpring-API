package com.example.keyspring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JweTokenService {

    private final SecretKey secretKey;

    public JweTokenService(@Value("${jwt.secret.key}") String secretKeyString) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String createJweToken() {
        return Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .issuer("key-spring")
                .subject("user")
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .notBefore(Date.from(Instant.now()))
                .issuedAt(new Date())
                .id("123")
                .signWith(secretKey)
                .compact();
    }
}
