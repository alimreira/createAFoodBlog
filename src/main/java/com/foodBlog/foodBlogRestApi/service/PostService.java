package com.foodBlog.foodBlogRestApi.service;

import com.foodBlog.foodBlogRestApi.payload.CommentDto;
import com.foodBlog.foodBlogRestApi.payload.PostDto;
import com.foodBlog.foodBlogRestApi.payload.PostPaginationResponse;

import java.util.List;

public interface PostService {

    PostDto createPost (PostDto postDto);

    //List<PostDto> createPosts (List<PostDto> postDto);

    List<PostDto> getAllPosts ();

    PostDto getPostById (long id);

    PostDto updateAPost (long id, PostDto postDto);

    void deletePost(Long id);

    List<PostDto> pages (int pageNo,int pageSize);

    PostPaginationResponse customisedPages (int pageNo, int pageSize);

    List<PostDto> sortPost (String sortBy, String sortDir);

    List<PostDto> pageAndSort (int pageNo,int pageSize,String sortDir,String sortBy);
}
