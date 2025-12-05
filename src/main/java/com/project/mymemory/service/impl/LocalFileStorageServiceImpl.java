package com.project.mymemory.service.impl;



import com.project.mymemory.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {

    private final Path storagePath;

    public LocalFileStorageServiceImpl() throws IOException {
        this.storagePath = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }

        String original = file.getOriginalFilename();
        if (original == null || original.trim().isEmpty()) {
            throw new IllegalArgumentException("File name is invalid.");
        }

        String safeName = System.currentTimeMillis() + "_" + original;
        Path target = storagePath.resolve(safeName);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        String baseUrl = "/uploads/";
        return baseUrl + safeName;
    }
}
