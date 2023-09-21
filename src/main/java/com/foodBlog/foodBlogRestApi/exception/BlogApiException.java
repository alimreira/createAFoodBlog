package com.foodBlog.foodBlogRestApi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogApiException extends RuntimeException{
    //this exception is to validate business logic
    private HttpStatus status;
    private String message;

    public BlogApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
