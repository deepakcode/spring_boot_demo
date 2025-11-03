package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> resourceNotFoundExceptionHander(ResourceNotFoundException ex){
        Map<String,Object> responseEntityMap = new HashMap<>();
        responseEntityMap.put("error", "ResourceNotFound");
        responseEntityMap.put("message", ex.getMessage());
        responseEntityMap.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseEntityMap,HttpStatus.NOT_FOUND);
    }
}
