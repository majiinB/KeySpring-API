package com.example.keyspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the response returned after a successful login attempt.
 * Contains the JWT token and its expiry time.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-24
 * @modified 2024-12-24
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiry;
}
