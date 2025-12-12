package com.project.mymemory.controllers;

import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.entitys.Category;
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
@SuppressWarnings("unused")
public class MemoryController {

    private final MemoryServiceImpl memoryServiceImpl;

    @GetMapping
    public ApiResponse<List<Memory>> getAll() {
        return new ApiResponse<>(200,
                "Get memories successfully",
                memoryServiceImpl.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Memory>> getMemoryById(@PathVariable Long id) {
        Memory memory = memoryServiceImpl.getById(id);

        if (memory == null) {
            throw notFound("Memory not found.");
        }

        ApiResponse<Memory> response = new ApiResponse<>(
                200,
                "Get memory successfully.",
                memory
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<Memory>> create(
            @PathVariable Long userId,
            @RequestBody Memory memory
    ) {
        Memory created = memoryServiceImpl.create(userId, memory);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201,
                        "Create memory successfully",
                        created
                ));
    }

    @PutMapping("/{userId}/{memoryId}")
    public ApiResponse<Memory> update(
            @PathVariable Long userId,
            @PathVariable Long memoryId,
            @RequestBody Memory memory
    ) {
        return new ApiResponse<>(200,
                "Update memory successfully",
                memoryServiceImpl.update(userId, memoryId, memory)
        );
    }

    @DeleteMapping("/{userId}/{memoryId}")
    public ApiResponse<String> delete(
            @PathVariable Long userId,
            @PathVariable Long memoryId
    ) {
        return new ApiResponse<>(200,
                "Delete memory successfully",
                memoryServiceImpl.delete(userId, memoryId)
        );
    }
}
