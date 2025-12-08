package com.project.mymemory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // generates constructor with all fields
@NoArgsConstructor   // generates empty constructor for JSON
public class AuthResponse {

    private String token;
    private String message;
    private UserResponse user; // <— NEW: user object
}
