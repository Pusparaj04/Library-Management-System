package com.sb.BookManagement.exceptions;


import com.sb.BookManagement.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //-> This is specialized version of controller advice, this will intercept any exception thrown in the entire application

public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) //pre defined exception class in "org.springframework.web.bind", we just intercept this exception (if any occur in our application) here and give reliable userfriendly response.
    public ResponseEntity<Map<String, String>> myMethodArgumentNotvalidException(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class) //we explicitly define this java exception class (ResourceNotFoundException) in this package ( com.sb.BookManagement.exceptions), and intercept such exception here
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(APIException.class) //we explicitly define this java exception class (APIException) in this package ( com.sb.BookManagement.exceptions), and intercept such exception here
    public ResponseEntity<APIResponse> myAPIException(APIException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    }


}
