package com.nhom1.appointment_service.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handler(MethodArgumentNotValidException e){
        Set<String> errors = new HashSet<>();
        e.getBindingResult().getFieldErrors()
            .forEach(error->errors.add(error.getDefaultMessage()));

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse.builder().validationErrors(errors).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e){
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
    }

}
