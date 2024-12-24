package com.example.keyspring.model.claim;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a user claim containing user-specific information.
 * This class is used to store and transfer user-related data within the application.
 * It includes fields for unique ID, email, first name, and last name.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-24
 * @modified 2024-12-24
 */
@Data
@AllArgsConstructor
public class UserClaim {
    private String uniqueId;
    private String email;
    private String firstName;
    private String lastName;
}
