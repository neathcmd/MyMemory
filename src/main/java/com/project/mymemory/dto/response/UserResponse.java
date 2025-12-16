package com.project.mymemory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    // We only return safe fields (NOT password)
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String role;
}
