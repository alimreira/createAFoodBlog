package com.foodBlog.foodBlogRestApi.service;

import com.foodBlog.foodBlogRestApi.domain.Comment;
import com.foodBlog.foodBlogRestApi.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment (long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsOfAPost (long postId);

    CommentDto fetchCommentOfAPostById (Long postId, Long commentId);

    CommentDto updateCommentOfAPost (Long postId, Long commentId,CommentDto commentDto);

    void deleteCommentOfAPost (Long postId, Long commentId);
}
