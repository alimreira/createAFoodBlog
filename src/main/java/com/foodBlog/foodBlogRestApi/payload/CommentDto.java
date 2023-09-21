package com.foodBlog.foodBlogRestApi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    @NotEmpty(message = "name field should not be null or empty")
    private String name;
    @NotEmpty(message = "email should not be null or empty")
    @Email
    private String email;
    @NotEmpty(message="the body field should not be empty")
    @Size(min = 10, message = "comment body must have a minimum of 10 characters")
    private String body;

    private PostDto post;

}
