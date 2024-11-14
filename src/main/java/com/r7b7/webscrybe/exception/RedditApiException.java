package com.r7b7.webscrybe.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RedditApiException extends RuntimeException{
    private final HttpStatus status;
    
    public RedditApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
