package com.foodBlog.foodBlogRestApi.controller;

import com.foodBlog.foodBlogRestApi.payload.CommentDto;
import com.foodBlog.foodBlogRestApi.service.serviceImpl.CommentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createCommentForPost (@PathVariable(name ="postId") long postId, @Valid @RequestBody CommentDto commentDto){
       CommentDto dto = commentServiceImpl.createComment(postId,commentDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/post/comments")
    public ResponseEntity<List<CommentDto>> commentsOfAPost (@RequestParam long postId) {
       List<CommentDto> dto =  commentServiceImpl.getAllCommentsOfAPost(postId);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> commentOfAPost (@PathVariable(value="postId") Long postId,
                                                      @PathVariable(value="commentId") Long commentId){
        CommentDto dto = commentServiceImpl.fetchCommentOfAPostById(postId,commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentOfAPost (@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                            @Valid @RequestBody CommentDto commentDto){
        CommentDto dto =commentServiceImpl.updateCommentOfAPost(postId,commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/post/delete/comment")
    public ResponseEntity<String> deleteCommentOfPost (@RequestParam Long postId,@RequestParam Long commentId) {

        commentServiceImpl.deleteCommentOfAPost(postId,commentId);
        return new ResponseEntity<>("Comment of post " + postId + " is deleted",HttpStatus.OK);
    }

}
