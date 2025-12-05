package com.project.mymemory.controllers;

import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.entitys.User;
import com.project.mymemory.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return new ApiResponse<>(
                "Users fetched successfully",
                userService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        return new ApiResponse<>(
                "User fetched successfully",
                userService.getById(id) // service already throws custom notFound
        );
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        return new ApiResponse<>(
                "User created successfully",
                userService.create(user)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return new ApiResponse<>(
                "User updated successfully",
                userService.update(id, user)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.delete(id); // if not found → custom error triggers
        return new ApiResponse<>(
                "User deleted successfully",
                null
        );
    }
}
