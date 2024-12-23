package com.example.keyspring.service;

import com.example.keyspring.model.User;
import com.example.keyspring.model.response.Response;
import com.example.keyspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final Argon2PasswordEncoder encoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    public Response register(User user){
        try{
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
            return new Response(
                    "500",
                    "An unexpected error occurred on the server. Please try again later.",
                    null);
        }
    }

    public String hashPassword(String password){
        return encoder.encode(password);
    }

    public boolean checkPassword(String rawPassword, String dbHashedPassword){
        return encoder.matches(rawPassword, dbHashedPassword);
    }

    public Boolean validateEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

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
        errors.put("status", "true");
        errors.put("message", "Password is valid.");
        return errors;
    }
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
}
