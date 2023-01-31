package com.mycompany.recipe.controller;

import com.mycompany.recipe.controller.model.ErrorResponse;
import com.mycompany.recipe.exception.RecipeNotFoundException;
import com.mycompany.recipe.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String serviceName;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable t) {
        logger.error("Unknown Exception", t);
        return createResponseEntityWithStatus(INTERNAL_SERVER_ERROR, t.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleThrowable(IOException ex) {
        logger.error("IOException", ex);
        return createResponseEntityWithStatus(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(UserNotFoundException ex) {
        logger.warn("UserNotFoundException", ex);
        return createResponseEntityWithStatus(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(RecipeNotFoundException ex) {
        logger.warn("RecipeNotFoundException", ex);
        return createResponseEntityWithStatus(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({MissingRequestValueException.class, MethodArgumentNotValidException.class,
            ServerWebInputException.class, HttpMessageNotReadableException.class,
            MissingRequestHeaderException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handleServerWebInputException(Exception ex) {
        logger.warn("Invalid request", ex);
        return createResponseEntityWithStatus(BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> createResponseEntityWithStatus(HttpStatus status, String msg) {
        return new ResponseEntity<>(new ErrorResponse(serviceName, status.value(), msg, LocalDateTime.now()), status);
    }
}
