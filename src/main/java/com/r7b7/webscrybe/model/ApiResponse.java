package com.r7b7.webscrybe.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String error;

    // Constructors
    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    // Factory methods for easy creation
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, error);
    }
}

