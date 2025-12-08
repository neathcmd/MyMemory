package com.project.mymemory.controllers;

import com.project.mymemory.dto.request.AuthRequest;
import com.project.mymemory.dto.request.RegisterRequest;
import com.project.mymemory.services.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authServiceImpl.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authServiceImpl.login(request));
    }
}
