package com.example.keyspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Long id;

    @Column(length = 3, nullable = false)
    @Getter @Setter
    private String prefix;

    @Column(name = "unique_id", length = 20, unique = true, nullable = false)
    @Getter
    private String uniqueId;

    @Column(length = 255, unique = true, nullable = false)
    @Getter @Setter
    private String email;

    @Column(length = 300, nullable = false)
    @Getter @Setter
    private String password;

    @Column(length = 100, nullable = false)
    @Getter @Setter
    private String firstName;

    @Column(length = 100, nullable = false)
    @Getter @Setter
    private String lastName;

    @Column(name = "phone_number", length = 15)
    @Getter @Setter
    private String phoneNumber;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Getter @Setter
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Getter @Setter
    private LocalDateTime updatedAt;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Getter @Setter
    private Boolean isActive = false;

    @Column(name = "is_verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Getter @Setter
    private Boolean isVerified = false;

    @Column(length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'user'")
    @Getter @Setter
    private String role = "user";

    @Column(name = "auth_provider", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'keySpring'")
    @Getter @Setter
    private String authProvider = "keySpring";

    @Column(name = "google_id", length = 300)
    @Getter @Setter
    private String googleId;

    @Column(name = "last_login_at")
    @Getter @Setter
    private LocalDateTime lastLoginAt;

    @Column(name = "password_reset_token", length = 300)
    @Getter @Setter
    private String passwordResetToken;

    @Column(name = "reset_token_expires_at")
    @Getter @Setter
    private LocalDateTime resetTokenExpiresAt;

    @Column(name = "failed_login_attempts", columnDefinition = "INTEGER DEFAULT 0")
    @Getter @Setter
    private Integer failedLoginAttempts = 0;

    @Column(name = "account_locked_until")
    @Getter @Setter
    private LocalDateTime accountLockedUntil;

    public User() {
    }

    public User(String prefix, String email, String password, String firstName, String lastName) {
        this.prefix = prefix;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
