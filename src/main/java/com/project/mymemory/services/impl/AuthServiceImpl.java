package com.project.mymemory.services.impl;

import com.project.mymemory.dto.request.AuthRequest;
import com.project.mymemory.dto.request.RegisterRequest;
import com.project.mymemory.dto.response.AuthResponse;
import com.project.mymemory.dto.response.UserResponse;
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

    // Fake JWT generator for now
    private String generateFakeJwt() {
        return "JWT_TOKEN_PLACEHOLDER";
    }

    // ---------------- REGISTER ---------------- //
    @Override
    public AuthResponse register(RegisterRequest request) {

        validateRegisterRequest(request);

        // Create User Entity
        User user = new User();
        user.setFullname(request.getFullname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        // Create token
        String token = generateFakeJwt();

        // Convert saved User to UserResponse DTO
        UserResponse userRes = new UserResponse(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail()
        );

        // Return AuthResponse (token + msg + user)
        return new AuthResponse(token, "Registration successful.", userRes);
    }


    // ---------------- LOGIN ---------------- //
    @Override
    public AuthResponse login(AuthRequest request) {

        // Find user or throw 404
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> notFound("User not found."));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw badRequest("Invalid email or password.");
        }

        String token = generateFakeJwt();

        // Map entity -> response DTO
        UserResponse userRes = new UserResponse(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail()
        );

        return new AuthResponse(token, "Login successful.", userRes);
    }


    // ---------------- HELPERS ---------------- //

    private void validateRegisterRequest(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw badRequest("Email is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw badRequest("Username is already in use.");
        }
    }
}
