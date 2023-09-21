package com.foodBlog.foodBlogRestApi.service.serviceImpl;

import com.foodBlog.foodBlogRestApi.domain.Comment;
import com.foodBlog.foodBlogRestApi.domain.Post;
import com.foodBlog.foodBlogRestApi.exception.BlogApiException;
import com.foodBlog.foodBlogRestApi.exception.ResourceNotFoundException;
import com.foodBlog.foodBlogRestApi.payload.CommentDto;
import com.foodBlog.foodBlogRestApi.repository.CommentRepository;
import com.foodBlog.foodBlogRestApi.repository.PostRepository;
import com.foodBlog.foodBlogRestApi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        //To check if post exist in the database
        Post postFetched = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set the post reference in the comment entity
        comment.setPost(postFetched);

        // Save the comment entity
        Comment commentSaved = commentRepository.save(comment);
        CommentDto commentDto1 = modelMapper.map(commentSaved, CommentDto.class);

        return commentDto1;
    }

    @Override
    public List<CommentDto> getAllCommentsOfAPost(long postId) {
        //retrieve comments by postId
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentDto> dtos = commentList.stream().map((comment)-> modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());
        //List<CommentDto> dtos = commentList.stream().map((m) -> new CommentDto(m.getId(), m.getName(), m.getEmail(), m.getBody())).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto fetchCommentOfAPostById (Long postId, Long commentId) {
        //null check for postId
      Post postFetched = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
      //null check for commentId
      Comment commentFetched = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
      //check if comment fetched belongs to Post
        if(!commentFetched.getPost().getId().equals(postFetched.getId())){
            String errorMessage = "Comment does not belong to post";
            throw new BlogApiException(HttpStatus.BAD_REQUEST, errorMessage);
        }
       CommentDto commentDto =  modelMapper.map(commentFetched,CommentDto.class);
        return commentDto;
    }

    @Override
    public CommentDto updateCommentOfAPost(Long postId, Long commentId, CommentDto commentDto) {
       Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
       Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

       if(!comment.getPost().getId().equals(post.getId())){
           throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to post");
       }
       // comment = modelMapper.map(commentDto,Comment.class);
       comment.setName(commentDto.getName());
       comment.setEmail(commentDto.getEmail());
       comment.setBody(commentDto.getBody());
       Comment saveComment = commentRepository.save(comment);

      CommentDto updatedCommentDto = modelMapper.map(saveComment,CommentDto.class);
        return updatedCommentDto;
    }

    @Override
    public void deleteCommentOfAPost(Long postId, Long commentId) {
       Post post =  postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

       Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

       if(!comment.getPost().equals(post)){
           throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
       }

        commentRepository.deleteById(commentId);

    }

}