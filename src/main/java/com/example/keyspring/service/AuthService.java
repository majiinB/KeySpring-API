package com.example.keyspring.service;

import com.example.keyspring.model.User;
import com.example.keyspring.model.response.LoginResponse;
import com.example.keyspring.model.response.Response;
import com.example.keyspring.model.claim.UserClaim;
import com.example.keyspring.repository.UserRepository;
import com.example.keyspring.security.JweTokenService;
import com.example.keyspring.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for handling user authentication operations such as registration, login, and password management.
 * Provides methods for validating user input (email, password) and processing user registration.
 * <p>
 * The service also includes password hashing and email validation logic.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-21
 * @modified 2024-12-24
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final JweTokenService jweTokenService;
    private final UserRepository userRepository;
    private final Argon2PasswordEncoder encoder;

    @Autowired
    public AuthService(UserRepository userRepository, JweTokenService jweTokenService) {
        this.userRepository = userRepository;
        this.jweTokenService = jweTokenService;
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
            Map<String, String> userValidation = ValidationUtils.validateUser(user);

            if(userValidation.get("status").equals("false")){
                return new Response(
                        "400",
                        userValidation.get("message"),
                        null);

            }
            if(!ValidationUtils.isValidEmailFormat(user.getEmail())){
                return new Response(
                        "400",
                        "Invalid email format.",
                        null);
            }

            if(validateExistenceOfEmail(user.getEmail())){
                return new Response(
                        "409",
                        "Email already exists.",
                        null);
            }

            Map<String, String> passwordValidation = ValidationUtils.validatePasswordStrength(
                    user.getPassword());
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
     * Authenticates a user based on the provided email and password.
     * <p>
     * This method validates the login credentials, checks if the user exists,
     * and returns a response indicating the success or failure of the login attempt.
     * </p>
     *
     * @param requestBody A map containing the email and password of the user.
     * @return A {@link Response} object containing the status and message of the login attempt.
     */
    public Response login(Map<String, String > requestBody){
        try{
            if(requestBody.isEmpty()){
                return new Response(
                        "400",
                        "Login failed. Email and password are required.",
                        null);
            }
            if(!requestBody.containsKey("email")||!requestBody.containsKey("password")){
                return new Response(
                        "400",
                        "Login failed. Email and password are required.",
                        null);
            }
            if(requestBody.get("email").isEmpty()||requestBody.get("password").isEmpty()){
                return new Response(
                        "400",
                        "Login failed. Email and password are required.",
                        null);
            }

            String email = requestBody.get("email");
            String password = requestBody.get("password");

            if(!ValidationUtils.isValidEmailFormat(email)){
                return new Response(
                        "400",
                        "Login failed. Invalid email format.",
                        null);
            }

            User dbUser = findUserByEmail(email);

            if(dbUser == null){
                return new Response(
                        "404",
                        "Login failed. User not found.",
                        null);
            }
            if(!validatePassword(password, dbUser.getPassword())){
                return new Response(
                        "401",
                        "Login failed. Invalid password.",
                        null);
            }

            Date expiresAt = Date.from(Instant.now().plusSeconds(3600));
            LoginResponse loginResponse = new LoginResponse(
                    jweTokenService.createJweToken(
                            new UserClaim(
                                    dbUser.getUnique_id(),
                                    dbUser.getEmail(),
                                    dbUser.getFirst_name(),
                                    dbUser.getLast_name()),
                            dbUser.getUnique_id(),
                            Date.from(Instant.now()),
                            expiresAt
                    ),
                    expiresAt.getTime()/1000
            );
            return new Response(
                    "200",
                    "Login successful.",
                    loginResponse);
        }catch (Exception e){
            // TODO: Save error logs to database
            logger.error("Error occurred during user login: {}", e.getMessage(), e);
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
    private boolean validatePassword(String rawPassword, String dbHashedPassword){
        return encoder.matches(rawPassword, dbHashedPassword);
    }

    /**
     * Validates the user's email to ensure it is unique.
     *
     * @param email The email to validate.
     * @return true if the email exists, false otherwise.
     */
    public Boolean validateExistenceOfEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The {@link User} object if found, otherwise returns null.
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
