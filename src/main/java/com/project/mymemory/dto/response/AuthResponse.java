package com.project.mymemory.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    private String token;
    private String message;

    public AuthResponse(String token, String s) {
    }
}
