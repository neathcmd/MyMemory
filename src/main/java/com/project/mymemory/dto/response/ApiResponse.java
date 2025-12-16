package com.project.mymemory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer status;
    private String message;
    private T data;
}
