package com.netceed.management.management_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> userNotFoundHandler(ApiRequestException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Europe/London"))
        );
        return new ResponseEntity<>(apiException, notFound);
    }
}
