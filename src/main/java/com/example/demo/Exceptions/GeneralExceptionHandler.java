package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) {
        if (ex instanceof GeneralException gx){
            if (Objects.equals(request.getHeader("lang"), "FA")){
                return gx.getFaResponse();
            } else {
                return gx.getEnResponse();
            }
        } else if (ex instanceof MethodArgumentNotValidException mx) {

            return ResponseEntity.status(400).body(mx.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("server cannot handle your request");
    }
}
