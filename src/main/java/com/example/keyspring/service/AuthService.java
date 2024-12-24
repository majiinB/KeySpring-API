package com.example.keyspring.service;

import com.example.keyspring.model.User;
import com.example.keyspring.model.response.Response;
import com.example.keyspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for handling user authentication operations such as registration, login, and password management.
 * Provides methods for validating user input (email, password) and processing user registration.
 *
 * The service also includes password hashing and email validation logic.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-21
 * @modified 2024-12-23
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final Argon2PasswordEncoder encoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    /**
     * Registers a new user after validating email and password.
     *
     * @param user User object containing registration details.
     * @return {@code Response} object indicating the result of the registration attempt.
     */
    public Response register(User user){
        try{
            if(!isValidEmailFormat(user.getEmail())){
                return new Response(
                        "400",
                        "Invalid email format.",
                        null);
            }

            if(validateEmail(user.getEmail())){
                return new Response(
                        "409",
                        "Email already exists.",
                        null);
            }

            Map<String, String> passwordValidation = validatePassword(user.getPassword());
            if(passwordValidation.get("status").equals("false")){
                return new Response(
                        "400",
                        passwordValidation.get("message"),
                        null);
            }

            user.setPrefix("ksl");
            user.setPassword(hashPassword(user.getPassword()));

            userRepository.save(user);
            return new Response(
                    "200",
                    "User registered successfully.",
                    null);
        }catch (Exception e){
            // TODO: Save error logs to database
            logger.error("Error occurred during user registration: {}", e.getMessage(), e);
            return new Response(
                    "500",
                    "An unexpected error occurred on the server. Please try again later.",
                    null);
        }
    }

    /**
     * Hashes the password using Argon2 algorithm.
     *
     * @param password The raw password to hash.
     * @return The hashed password.
     */
    public String hashPassword(String password){
        return encoder.encode(password);
    }

    /**
     * Checks if the provided raw password matches the hashed password stored in the database.
     *
     * @param rawPassword The raw password provided by the user.
     * @param dbHashedPassword The hashed password stored in the database.
     * @return true if the passwords match, false otherwise.
     */
    public boolean checkPassword(String rawPassword, String dbHashedPassword){
        return encoder.matches(rawPassword, dbHashedPassword);
    }

    /**
     * Validates the user's email to ensure it is unique.
     *
     * @param email The email to validate.
     * @return true if the email exists, false otherwise.
     */
    public Boolean validateEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    /**
    * Validates the strength of the password.
    * Ensures the password meets various strength requirements.
    *
    * @param password The password to validate.
    * @return A {@code Map} containing the validation status and any relevant error message.
    */
    public Map<String, String> validatePassword(String password) {
        Map<String, String> errors = new HashMap<>();
        if (password.length() < 8) {
            errors.put("status", "false");
            errors.put("message", "Password must be at least 8 characters long.");
            return errors;
        }
        if (!password.matches(".*\\d.*")) {
            errors.put("status", "false");
            errors.put("message", "Password must contain at least one digit.");
            return errors;
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            errors.put("status", "false");
            errors.put("message", "Password must contain at least one special character.");
            return errors;
        }
        if (password.matches(".*\\s.*")) {
            errors.put("status", "false");
            errors.put("message", "Password must not contain any white spaces.");
            return errors;
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.put("status", "false");
            errors.put("message", "Password must contain at least one uppercase letter.");
            return errors;
        }
        errors.put("status", "true");
        errors.put("message", "Password is valid.");
        return errors;
    }

    /**
     * Validates the provided user fields to ensure all necessary information is present.
     *
     * @param user The user to validate.
     * @return A map containing the validation status and any relevant error message.
     */
    public Map<String, String> validateUser(User user) {
        Map<String, String> errors = new HashMap<>();
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.put("status", "false");
            errors.put("message", "Email is required.");
            return errors;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.put("status", "false");
            errors.put("message", "Password is required.");
            return errors;
        }
        if (user.getFirst_name() == null || user.getFirst_name().isEmpty()) {
            errors.put("status", "false");
            errors.put("message", "First name is required.");
            return errors;
        }
        if (user.getLast_name() == null || user.getLast_name().isEmpty()) {
            errors.put("status", "false");
            errors.put("message", "Last name is required.");
            return errors;
        }
        errors.put("status", "true");
        errors.put("message", "User is valid.");
        return errors;
    }

    /**
     * Checks if the email format is valid.
     *
     * @param email The email to check.
     * @return true if the email format is valid, false otherwise.
     */
    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }
}
