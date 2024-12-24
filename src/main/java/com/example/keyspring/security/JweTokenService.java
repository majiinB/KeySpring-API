package com.example.keyspring.security;

import com.example.keyspring.model.claim.UserClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service class for handling JSON Web Encryption (JWE) token operations.
 * This class provides methods to create, read, and extract information from JWE tokens.
 * It uses a secret key for signing and verifying the tokens.
 * <p>
 * The secret key is injected from the application properties.
 * </p>
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-22
 * @modified 2024-12-24
 */
@Service
public class JweTokenService {

    private final SecretKey secretKey;

    /**
     * Constructs a new JweTokenService with the specified secret key.
     *
     * @param secretKeyString the secret key string used for signing and verifying tokens.
     */
    public JweTokenService(@Value("${jwt.secret.key}") String secretKeyString) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    /**
     * Creates a JWE token with the specified claims and timestamps.
     *
     * @param userClaim the user claim to be included in the token.
     * @param uniqueId the unique ID of the user.
     * @param issAt the issuance time of the token.
     * @param expAt the expiration time of the token.
     * @return the generated JWE token as a string.
     */
    public String createJweToken(
            UserClaim userClaim,
            String uniqueId,
            Date issAt,
            Date expAt) {
        try{
            return Jwts.builder()
                    .header()
                    .add("alg", "HS256")
                    .add("typ", "JWT")
                    .and()
                    .issuer("key-spring")
                    .subject(uniqueId)
                    .claim("User", userClaim)
                    .expiration(expAt)
                    .notBefore(issAt)
                    .issuedAt(issAt)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    /**
     * Reads and parses a JWE token.
     *
     * @param jweToken the JWE token to be read.
     * @return the parsed JWS containing the claims, or null if an error occurs.
     */
    public Jws<Claims> readJWE(String jweToken) {
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

    /**
     * Extracts the subject (unique ID) from a JWE token.
     *
     * @param jweToken the JWE token from which to extract the subject.
     * @return the subject (unique ID) of the token, or null if an error occurs.
     */
    public String extractSubject(String jweToken) {
        Jws<Claims> readToken = readJWE(jweToken);
        if(readToken == null) return null;
        return readToken.getPayload().getSubject();
    }
}