package com.foodBlog.foodBlogRestApi.payload.customErrorResponse;

import com.foodBlog.foodBlogRestApi.exception.BlogApiException;
import com.foodBlog.foodBlogRestApi.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException (ResourceNotFoundException resourceNotFoundException,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiExceptionException (BlogApiException blogApiException,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                blogApiException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException (Exception exception,
                                                               WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        //we will create a map to hold fields and their validation messages.
        //we will get validation errors from exception objects i.e ex
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream().forEach((error)-> {
            String fieldName = ((FieldError)error).getField();
            String validationMessage = error.getDefaultMessage();
            errors.put(fieldName,validationMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


}
