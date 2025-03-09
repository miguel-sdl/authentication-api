package com.example.authentication_api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionResponse {
    private int status;
    private String title;
    private String message;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "{" +
                " \"status\": " + status +
                ",\n \"title\": \"" + title +
                "\",\n \"message\": \"" + message +
                "\",\n \"timestamp\": \"" + timestamp +
                "\"}";
    }
}
