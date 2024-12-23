package com.example.keyspring.api.controller;

import com.example.keyspring.model.User;
import com.example.keyspring.model.response.Response;
import com.example.keyspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/// Controller responsible for handling authentication-related operations such as user registration and login.
/// Provides endpoints for both regular registration and Google-based registration.
///
/// `Author` Arthur Artugue
///
/// `version` 1.0
///
/// `since` 2024-12-21
///
/// `modified` 2024-12-23
@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /// Constructs an [AuthController] instance with the provided [AuthService].
    ///
    /// @param authService The authentication service that handles the business logic for user registration and validation.
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /// Registers a user to KeySpring.
    ///
    /// This endpoint validates the user data, registers the user if validation passes, and returns a response
    /// indicating the success or failure of the registration.
    ///
    /// @param user The user details to be registered.
    /// @return A `ResponseEntity` containing the status and message of the registration attempt.
    @PostMapping(path = "/register/keySpring")
    public ResponseEntity<Response> registerToKeySpring(@RequestBody User user) {
        Map<String, String> userValidation = authService.validateUser(user);

        if(userValidation.get("status").equals("false")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(
                    "400",
                    userValidation.get("message"),
                    null)
            );
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
