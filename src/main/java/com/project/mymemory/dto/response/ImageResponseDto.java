package com.project.mymemory.dto.response;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponseDto {
    private Long id;
    private String url;
    private String fileName;
}