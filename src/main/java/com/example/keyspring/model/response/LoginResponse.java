package com.example.keyspring.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiry;
}
