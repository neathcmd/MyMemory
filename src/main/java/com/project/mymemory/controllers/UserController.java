package com.project.mymemory.controllers;

import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.entitys.User;
import com.project.mymemory.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@SuppressWarnings("unused")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    // ============ GET ALL ============ //
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return new ApiResponse<>(
                "Users retrieved successfully.",
                userServiceImpl.getAll()
        );
    }

    // ============ GET BY ID ============ //
    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        return new ApiResponse<>(
                "User retrieved successfully.",
                userServiceImpl.getById(id)
        );
    }

    // ============ CREATE USER ============ //
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        return new ApiResponse<>(
                "User created successfully.",
                userServiceImpl.create(user)
        );
    }

    // ============ UPDATE USER ============ //
    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser
    ) {
        return new ApiResponse<>(
                "User updated successfully.",
                userServiceImpl.update(id, updatedUser)
        );
    }

    // ============ DELETE USER ============ //
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userServiceImpl.delete(id);
        return new ApiResponse<>(
                "User deleted successfully.",
                null
        );
    }
}
