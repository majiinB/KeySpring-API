package com.example.keyspring.api.controller;

import com.example.keyspring.model.User;
import com.example.keyspring.model.response.Response;
import com.example.keyspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register/keySpring")
    public ResponseEntity<Response> registerToKeySpring(@RequestBody User user) {
        Map<String, String> userValidation = authService.validateUser(user);

        if(userValidation.get("status").equals("false")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(
                    "400",
                    userValidation.get("message"),
                    null));

        }

        Response response = authService.register(user);

        return switch (response.getStatus()) {
            case "409" -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            case "400" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            case "200" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
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
