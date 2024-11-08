package com.netceed.management.management_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<ErrorResponse> emailExistsException(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> argumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ErrorResponse apiError = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }



}
