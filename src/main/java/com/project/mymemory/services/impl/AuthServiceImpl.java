package com.project.mymemory.services.impl;

import com.project.mymemory.dto.request.AuthRequest;
import com.project.mymemory.dto.request.RegisterRequest;
import com.project.mymemory.dto.response.AuthResponse;
import com.project.mymemory.dto.response.RegisterResponse;
import com.project.mymemory.dto.response.UserResponse;
import com.project.mymemory.entitys.User;
import com.project.mymemory.repository.UserRepository;
import com.project.mymemory.services.AuthService;
import com.project.mymemory.services.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.mymemory.exception.ErrorsException.badRequest;
import static com.project.mymemory.exception.ErrorsException.notFound;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // ---------------- REGISTER ---------------- //
    @Override
    public RegisterResponse register(RegisterRequest request) {

        validateRegisterRequest(request);

        // Create User Entity
        User user = new User();
        user.setFullname(request.getFullname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("user");

        userRepository.save(user);

        // Convert saved User to UserResponse DTO
        UserResponse userRes = new UserResponse(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );

        // Return AuthResponse (token + msg + user)
        return new RegisterResponse("Registration successful.", userRes);
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

        // Map entity -> response DTO
        UserResponse userRes = new UserResponse(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );

        // Create token
        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole()
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
