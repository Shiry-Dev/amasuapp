package com.lacontraloria.amasuapp.adapters.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException notFound) {
       ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), notFound.getMessage());
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
