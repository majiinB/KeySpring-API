package com.example.keyspring.api.controller;

import com.example.keyspring.model.User;
import com.example.keyspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register/keySpring")
    public void registerToKeySpring(@RequestBody User user) {
        authService.register(user);
    }

    @PostMapping(path = "/register/google")
    public void registerFromGoogle(@RequestBody User user) {
        System.out.println("Register from Google");
    }

    @PostMapping(path = "/login")
    public void login() {
        System.out.println("Login");
    }




}
