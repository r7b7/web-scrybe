package com.r7b7.webscrybe.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
     private String message;
    private int statusCode;
    private LocalDateTime timestamp;
}
