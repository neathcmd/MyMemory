package com.project.mymemory.controllers;

import com.project.mymemory.dto.request.ImageUrlRequestDto;
import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.dto.response.ImageResponseDto;
import com.project.mymemory.services.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageServiceIml;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ImageResponseDto>> uploadImage(
            @ModelAttribute ImageUrlRequestDto requestDto
    ) {
        ImageResponseDto imageDto = imageServiceIml.uploadImage(requestDto);

        ApiResponse<ImageResponseDto> response = new ApiResponse<>(
                201,
                "Image uploaded successfully",
                imageDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ImageResponseDto>>> getAllImages() {
        List<ImageResponseDto> images = imageServiceIml.getAllImages();

        ApiResponse<List<ImageResponseDto>> response = new ApiResponse<>(
                200,
                "All images fetched successfully",
                images
        );

        return ResponseEntity.ok(response);
    }
}
