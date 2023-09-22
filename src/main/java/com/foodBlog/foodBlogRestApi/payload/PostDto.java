package com.foodBlog.foodBlogRestApi.payload;

import com.foodBlog.foodBlogRestApi.domain.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min=2, message ="post title should have atleast 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10, message = "post description should have atleast 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments = new HashSet<>();

}
