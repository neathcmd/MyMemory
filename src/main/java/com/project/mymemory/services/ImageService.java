package com.project.mymemory.services;

import com.project.mymemory.dto.response.ImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageResponseDto uploadImage(String url, MultipartFile file);
    List<ImageResponseDto> getAllImages();
}
