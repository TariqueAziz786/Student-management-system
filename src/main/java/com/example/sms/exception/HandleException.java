package com.example.sms.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    // var map = new HashMap<String, String>();
        // map.put("title", "Not found");
        // map.put("detail", e.getMessage());
        // map.put("timemap", LocalDateTime.now().toString());
        // return map;

    @ExceptionHandler(StudentNotFoundException.class)
   // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleNoSuchElementException(StudentNotFoundException e){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setProperty("timeStamp", LocalDateTime.now());
        return problemDetail;
        
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail HttpRequestNotSupportedException(HttpRequestMethodNotSupportedException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handler(MethodArgumentNotValidException e) {
        var details = new StringJoiner(",");

        e.getAllErrors().forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            var fieldName = ((FieldError) error).getField();
            details.add(fieldName + "->" +errorMessage);
        });
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, details.toString());
            problemDetail.setTitle("invalid data");
            problemDetail.setProperty("timeStamp", LocalDateTime.now());
            return problemDetail;

    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }
}
