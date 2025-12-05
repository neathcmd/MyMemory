package com.project.mymemory.services;

import com.project.mymemory.dto.request.AuthRequest;
import com.project.mymemory.dto.request.RegisterRequest;
import com.project.mymemory.entitys.User;
import com.project.mymemory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.project.mymemory.dto.response.Errors.notFound;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // -------------------------
    // CRUD USERS
    // -------------------------
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> notFound("User with" + id + "not found."));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User request) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(request.getUsername());
            user.setFullname(request.getFullname());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            return userRepository.save(user);
        }).orElse(null);
    }

    public void delete(Long id) {
        User existUser = getById(id);
        userRepository.deleteById(existUser.getId());
    }
}