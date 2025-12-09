package com.project.mymemory.controllers;

import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.dto.response.ImageResponseDto;
import com.project.mymemory.services.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageServiceImpl;

    // Upload image
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ImageResponseDto>> uploadImage(
            @RequestParam(required = false) String url,
            @RequestParam(required = false) MultipartFile file
    ) {
        ImageResponseDto imageDto = imageServiceImpl.uploadImage(url, file);
        ApiResponse<ImageResponseDto> response = new ApiResponse<>("Image uploaded successfully", imageDto);
        return ResponseEntity.ok(response);
    }

    // Get all images
    @GetMapping
    public ResponseEntity<ApiResponse<List<ImageResponseDto>>> getAll() {
        List<ImageResponseDto> images = imageServiceImpl.getAllImages();
        ApiResponse<List<ImageResponseDto>> response = new ApiResponse<>("All images fetched successfully", images);
        return ResponseEntity.ok(response);
    }
}
