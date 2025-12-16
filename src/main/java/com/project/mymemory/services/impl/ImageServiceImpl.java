package com.project.mymemory.services.impl;

import com.project.mymemory.dto.request.ImageUrlRequestDto;
import com.project.mymemory.dto.response.ImageResponseDto;
import com.project.mymemory.entitys.Image;
import com.project.mymemory.exception.ErrorsException;
import com.project.mymemory.repository.ImageRepository;
import com.project.mymemory.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public ImageResponseDto uploadImage(ImageUrlRequestDto requestDto) {

        String url = requestDto.getUrl();

        if (url == null || url.isBlank()) {
            throw ErrorsException.badRequest("Image URL is required");
        }

        String finalUrl = url.trim();

        // Validate URL
        try {
            URL validatedUrl = new URL(finalUrl);
            if (!validatedUrl.getProtocol().equals("http")
                    && !validatedUrl.getProtocol().equals("https")) {
                throw ErrorsException.badRequest("URL must start with http:// or https://");
            }
        } catch (MalformedURLException e) {
            log.error("Invalid URL: {}", finalUrl);
            throw ErrorsException.badRequest("Invalid URL format");
        }

        // Save to DB
        try {
            Image image = new Image();
            image.setUrl(finalUrl);
            image.setCreatedAt(LocalDateTime.now());

            Image saved = imageRepository.save(image);

            log.info("Image URL saved: {}", finalUrl);

            return new ImageResponseDto(
                    saved.getId(),
                    saved.getUrl(),
                    null
            );

        } catch (Exception e) {
            log.error("Failed to save image URL", e);
            throw ErrorsException.internal("Failed to save image URL");
        }
    }

    @Override
    public List<ImageResponseDto> getAllImages() {
        return imageRepository.findAll()
                .stream()
                .map(i -> new ImageResponseDto(
                        i.getId(),
                        i.getUrl(),
                        null
                ))
                .collect(Collectors.toList());
    }
}
