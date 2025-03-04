package com.example.authentication_api.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("BadRequestException foi lancada");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Usuario ja existe")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("UserAlreadyExistsException foi lancada");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ExceptionResponse> handleJWTVerificationException(JWTVerificationException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("Token JWT invalido")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("JWTVerificationException foi lancada");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<ExceptionResponse> handleMissingTokenException(MissingTokenException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("Token JWT nao encontrado no header Authorization")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("MissingTokenException foi lancada");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
