package com.example.keyspring.api.controller;

import com.example.keyspring.model.User;
import com.example.keyspring.model.response.Response;
import com.example.keyspring.security.JweTokenService;
import com.example.keyspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller responsible for handling authentication-related operations such as user registration and login.
 * Provides endpoints for both regular registration and Google-based registration.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-21
 * @modified 2024-12-24
 */
@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JweTokenService jweTokenService;

   /**
    * Constructs an {@code AuthController} instance with the provided {@code AuthService}.
    *
    * @param authService The authentication service that handles the business logic for user registration and validation.
    */
    @Autowired
    public AuthController(AuthService authService, JweTokenService jweTokenService) {
        this.authService = authService;
        this.jweTokenService = jweTokenService;
    }

    /**
     * Registers a user to KeySpring.
     * <p>
     * This endpoint validates the user data, registers the user if validation passes, and returns a response
     * indicating the success or failure of the registration.
     *
     * @param user The user details to be registered.
     * @return A {@link ResponseEntity} containing the status and message of the registration attempt.
     */
    @PostMapping(path = "/register/keySpring")
    public ResponseEntity<Response> registerToKeySpring(@RequestBody User user) {

        Response response = authService.register(user);

        return switch (response.getStatus()) {
            case "409" -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            case "400" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            case "200" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @PostMapping(path = "/register/google")
    public void registerFromGoogle() {

    }

    /**
     * Authenticates a user based on the provided email and password.
     * <p>
     * This endpoint validates the login credentials, checks if the user exists,
     * and returns a response indicating the success or failure of the login attempt.
     *
     * @param requestBody A map containing the email and password of the user.
     * @return A {@link ResponseEntity} containing the status and message of the login attempt.
     */
    @PostMapping(path = "/login")
    public ResponseEntity<Response> login(@RequestBody Map<String, String> requestBody) {
        Response response = authService.login(requestBody);

        return switch (response.getStatus()) {
            case "404" -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            case "401" -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            case "400" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            case "200" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }
}
