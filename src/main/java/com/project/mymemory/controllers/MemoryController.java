package com.project.mymemory.controllers;

import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.dto.response.ErrorsException;
import com.project.mymemory.services.impl.MemoryServiceImpl;
import com.project.mymemory.entitys.Memory;
import com.project.mymemory.repository.MemoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memories")
@SuppressWarnings("unused")
public class MemoryController {

    private final MemoryServiceImpl memoryServiceImpl;
    private final MemoryRepository memoryRepository;

    // GET ALL
    @GetMapping
    public ApiResponse<List<Memory>> getAllMemory() {
        List<Memory> memories = memoryServiceImpl.getAll();

        if (memories.isEmpty()) {
            throw ErrorsException.notFound("Memory not found.");
        }

        return new ApiResponse<>(
                "Get memory successfully",
                memories
        );
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<Memory>> createMemory(@PathVariable Long userId, @RequestBody Memory memory) {
        Memory createdMemory = memoryServiceImpl.createMemory(userId, memory);

        ApiResponse<Memory> response = new ApiResponse<>(
                "Create memory successfully",
                createdMemory
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
