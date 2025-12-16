package com.project.mymemory.services;

import com.project.mymemory.dto.request.ImageUrlRequestDto;
import com.project.mymemory.dto.response.ImageResponseDto;

import java.util.List;

public interface ImageService {
    ImageResponseDto uploadImage(ImageUrlRequestDto requestDto);
    List<ImageResponseDto> getAllImages();
}
