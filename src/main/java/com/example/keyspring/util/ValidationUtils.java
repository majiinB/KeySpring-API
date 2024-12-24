package com.example.keyspring.util;

import com.example.keyspring.model.User;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class responsible for validating user input such as email and password.
 * Provides methods for checking password strength, validating user fields, and verifying email format.
 * <p>
 * This class includes static methods to ensure that user data meets the required criteria.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-21
 * @modified 2024-12-24
 */
@UtilityClass
public class ValidationUtils {

    /**
     * Validates the strength of the password.
     * Ensures the password meets various strength requirements.
     *
     * @param password The password to validate.
     * @return A {@code Map} containing the validation status and any relevant error message.
     */
    public static Map<String, String> validatePasswordStrength(String password) {
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
    public static Map<String, String> validateUser(User user) {
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
    public static Boolean isValidEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }
}
