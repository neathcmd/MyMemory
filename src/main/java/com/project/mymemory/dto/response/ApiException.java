package com.project.mymemory.dto.response;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Getter
public class ApiException extends RuntimeException {
    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}

// ======= GLOBAL EXCEPTION HANDLER (NOT public) ======= //
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<String>> handleApiException(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleUnexpected(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ApiResponse<>("Internal server error", null));
    }
}
