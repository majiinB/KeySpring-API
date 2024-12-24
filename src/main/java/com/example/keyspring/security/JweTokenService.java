package com.example.keyspring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public String createJweToken(String subject) {
        try{
            return Jwts.builder()
                    .header()
                    .add("alg", "HS256")
                    .add("typ", "JWT")
                    .and()
                    .issuer("key-spring")
                    .subject(subject)
                    .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                    .notBefore(Date.from(Instant.now()))
                    .issuedAt(new Date())
                    .id("123")
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    private Jws<Claims> readJWE(String jweToken) {
        try{
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jweToken);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public String extractSubject(String jweToken) {
        Jws<Claims> readToken = readJWE(jweToken);
        if(readToken == null) return null;
        return readToken.getPayload().getSubject();
    }


}
