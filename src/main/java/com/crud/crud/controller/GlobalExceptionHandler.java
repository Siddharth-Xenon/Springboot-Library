package com.crud.crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.crud.crud.exceptions.BookNotFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // Define ApiError class within the same file or separately as needed
    public static class ApiError {
        private HttpStatus status;
        private String message;
        private List<String> errors;

        public ApiError(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }

        public ApiError(HttpStatus status, String message, List<String> errors) {
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }
}