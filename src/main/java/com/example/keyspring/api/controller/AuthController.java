package com.example.keyspring.api.controller;

import com.example.keyspring.security.JweTokenService;
import com.example.keyspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JweTokenService jweTokenService;

    @Autowired
    public AuthController(AuthService authService, JweTokenService jweTokenService) {
        this.authService = authService;
        this.jweTokenService = jweTokenService;
    }

    @PostMapping(path = "register")
    public void register(){
        try{
            System.out.println(jweTokenService.createJweToken());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @PostMapping(path = "login")
    public void login() {
        System.out.println("Login");
    }

    @GetMapping(path = "logout")
    public void logout() {
        System.out.println("Logout");
    }



}
