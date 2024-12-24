package com.example.keyspring.model.claim;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserClaim {
    private String uniqueId;
    private String email;
    private String firstName;
    private String lastName;
}
