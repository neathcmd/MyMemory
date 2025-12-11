package com.project.mymemory.services.impl;

import com.project.mymemory.entitys.User;
import com.project.mymemory.repository.UserRepository;
import com.project.mymemory.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.mymemory.exception.ErrorsException.badRequest;
import static com.project.mymemory.exception.ErrorsException.notFound;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ===================== GET ALL ===================== //
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // ===================== GET BY ID ===================== //
    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> notFound("User with ID " + id + " not found."));
    }

    // ===================== CREATE ===================== //
    @Override
    public User create(User request) {
        validateCreate(request);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(request);
    }

    // ===================== UPDATE ===================== //
    @Override
    public User update(Long id, User request) {
        User existing = getById(id);
        validateUpdate(id, request);

        existing.setFullname(request.getFullname());
        existing.setUsername(request.getUsername());
        existing.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(existing);
    }

    // ===================== DELETE ===================== //
    @Override
    public void delete(Long id) {
        User existing = getById(id);
        userRepository.delete(existing);
    }

    // ===================== VALIDATION ===================== //

    private void validateEmailFormat(String email) {
        if (email == null || !email.contains("@") || !email.endsWith(".com")) {
            throw badRequest("Email must contain '@' and end with '.com'");
        }
    }

    private void validateCreate(User request) {
        validateEmailFormat(request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw badRequest("Email is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw badRequest("Username is already in use.");
        }
    }

    private void validateUpdate(Long id, User request) {
        validateEmailFormat(request.getEmail());

        userRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw badRequest("Email is already used by another user.");
            }
        });

        userRepository.findByUsername(request.getUsername()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw badRequest("Username is already used by another user.");
            }
        });
    }
}
