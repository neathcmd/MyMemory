package com.project.mymemory.services.impl;

import com.project.mymemory.dto.response.ErrorsException;
import com.project.mymemory.dto.response.ImageResponseDto;
import com.project.mymemory.entitys.Image;
import com.project.mymemory.repository.ImageRepository;
import com.project.mymemory.services.FileStorageService;
import com.project.mymemory.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ImageResponseDto uploadImage(String url, MultipartFile file) {

        if ((url == null || url.isBlank()) && (file == null || file.isEmpty())) {
            throw ErrorsException.badRequest("Please provide a file OR a valid URL.");
        }

        String finalUrl;
        String fileName = null;

        // File upload
        if (file != null && !file.isEmpty()) {
            try {
                finalUrl = fileStorageService.storeFile(file);
                fileName = file.getOriginalFilename();
                log.info("File uploaded successfully: {}", fileName);
            } catch (IOException e) {
                log.error("Failed to upload file: {}", e.getMessage(), e);
                throw ErrorsException.internal("Failed to upload file.");
            }
        }
        // URL validation
        else {
            finalUrl = url.trim();
            try {
                URL validatedUrl = new URL(finalUrl);
                if (!validatedUrl.getProtocol().equals("http") && !validatedUrl.getProtocol().equals("https")) {
                    throw ErrorsException.badRequest("URL must start with http:// or https://");
                }
            } catch (MalformedURLException e) {
                log.error("Invalid URL provided: {}", finalUrl);
                throw ErrorsException.badRequest("Please provide a file OR a valid URL.");
            }
            log.info("Using provided URL: {}", finalUrl);
        }

        // Save to DB without builder
        try {
            Image image = new Image();
            image.setUrl(finalUrl);
            image.setFileName(fileName);
            image.setCreatedAt(LocalDateTime.now());

            Image saved = imageRepository.save(image);
            return new ImageResponseDto(saved.getId(), saved.getUrl(), saved.getFileName());
        } catch (Exception e) {
            log.error("Failed to save image: {}", e.getMessage(), e);
            throw ErrorsException.internal("Failed to save image.");
        }
    }

    @Override
    public List<ImageResponseDto> getAllImages() {
        return imageRepository.findAll().stream()
                .map(i -> new ImageResponseDto(i.getId(), i.getUrl(), i.getFileName()))
                .collect(Collectors.toList());
    }
}
