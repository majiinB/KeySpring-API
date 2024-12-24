package com.example.keyspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a user entity in the application.
 * This class is mapped to the "users" table in the database.
 * It includes fields for user details such as email, password, name, and various status flags.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-21
 * @modified 2024-12-24
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "unique_id", length = 20, unique = true, insertable = false, updatable = false)
    @Getter
    private String unique_id;

    @Column(length = 3)
    @Getter @Setter
    private String prefix;


    @Column(length = 255, unique = true, nullable = false)
    @Getter @Setter
    private String email;

    @Column(length = 300, nullable = false)
    @Getter @Setter
    private String password;

    @Column(length = 100, nullable = false)
    @Getter @Setter
    private String first_name;

    @Column(length = 100, nullable = false)
    @Getter @Setter
    private String last_name;

    @Column(name = "phone_number", length = 15)
    @Getter @Setter
    private String phone_number;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Getter @Setter
    private LocalDateTime created_at;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Getter @Setter
    private LocalDateTime updated_at;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Getter @Setter
    private Boolean is_active = false;

    @Column(name = "is_verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Getter @Setter
    private Boolean is_verified = false;

    @Column(length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'user'")
    @Getter @Setter
    private String role = "user";

    @Column(name = "auth_provider", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'keySpring'")
    @Getter @Setter
    private String auth_provider = "keySpring";

    @Column(name = "google_id", length = 300)
    @Getter @Setter
    private String google_id;

    @Column(name = "last_login_at")
    @Getter @Setter
    private LocalDateTime last_login_at;

    @Column(name = "password_reset_token", length = 300)
    @Getter @Setter
    private String password_reset_token;

    @Column(name = "reset_token_expires_at")
    @Getter @Setter
    private LocalDateTime reset_token_expires_at;

    @Column(name = "failed_login_attempts", columnDefinition = "INTEGER DEFAULT 0")
    @Getter @Setter
    private Integer failed_login_attempts = 0;

    @Column(name = "account_locked_until")
    @Getter @Setter
    private LocalDateTime account_locked_until;

    public User() {
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.first_name = firstName;
        this.last_name = lastName;
    }

    public User(String email, String password, String firstName, String lastName, String googleId) {
        this.email = email;
        this.password = password;
        this.first_name = firstName;
        this.last_name = lastName;
        this.google_id = googleId;
    }
}
