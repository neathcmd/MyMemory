package com.project.mymemory.controllers;

import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.entitys.Memory;
import com.project.mymemory.services.impl.MemoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.project.mymemory.exception.ErrorsException.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memories")
public class MemoryController {

    private final MemoryServiceImpl memoryServiceImpl;

    @GetMapping
    public ApiResponse<List<Memory>> getAll() {
        return new ApiResponse<>("Get memories successfully", memoryServiceImpl.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<Memory> getMemoryById(@PathVariable Long id) {
        Memory memory = memoryServiceImpl.getById(id);

        if (memory == null) {
            throw notFound("Memory not found.");
        }

        return new ApiResponse<>(
                "Get memory successfully.",
                memory
        );
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<Memory>> create(
            @PathVariable Long userId,
            @RequestBody Memory memory
    ) {
        Memory created = memoryServiceImpl.create(userId, memory);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Create memory successfully", created));
    }

    @PutMapping("/{userId}/{memoryId}")
    public ApiResponse<Memory> update(
            @PathVariable Long userId,
            @PathVariable Long memoryId,
            @RequestBody Memory memory
    ) {
        return new ApiResponse<>("Update memory successfully", memoryServiceImpl.update(userId, memoryId, memory));
    }

    @DeleteMapping("/{userId}/{memoryId}")
    public ApiResponse<String> delete(
            @PathVariable Long userId,
            @PathVariable Long memoryId
    ) {
        return new ApiResponse<>("Delete memory successfully", memoryServiceImpl.delete(userId, memoryId));
    }
}
