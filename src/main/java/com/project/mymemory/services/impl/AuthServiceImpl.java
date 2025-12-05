package com.project.mymemory.services.impl;

import com.project.mymemory.dto.request.AuthRequest;
import com.project.mymemory.dto.request.RegisterRequest;
import com.project.mymemory.dto.response.AuthResponse;
import com.project.mymemory.entitys.User;
import com.project.mymemory.repository.UserRepository;
import com.project.mymemory.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.mymemory.dto.response.ErrorsException.badRequest;
import static com.project.mymemory.dto.response.ErrorsException.notFound;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private String generateFakeJwt() {
        return "JWT_TOKEN_PLACEHOLDER";
    }

    // ===================== REGISTER ===================== //
    @Override
    public AuthResponse register(RegisterRequest request) {

        validateRegisterRequest(request);

        User user = new User();
        user.setFullname(request.getFullname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Always encode password!

        userRepository.save(user);

        String token = generateFakeJwt();

        return new AuthResponse(token, "Registration successful.");
    }

    // ===================== LOGIN ===================== //
    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> notFound("User not found."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw badRequest("Invalid email or password.");
        }

        String token = generateFakeJwt();

        return new AuthResponse(token, "Login successful.");
    }

    // ===================== PRIVATE HELPERS ===================== //

    private void validateRegisterRequest(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw badRequest("Email is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw badRequest("Username is already in use.");
        }
    }
}
