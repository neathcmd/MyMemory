package com.project.mymemory.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ImageUrlRequestDto {

    @NotBlank(message = "URL cannot be blank")
    private String url;
}
