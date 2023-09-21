package com.foodBlog.foodBlogRestApi.controller;

import com.foodBlog.foodBlogRestApi.payload.CommentDto;
import com.foodBlog.foodBlogRestApi.payload.PostDto;
import com.foodBlog.foodBlogRestApi.payload.PostPaginationResponse;
import com.foodBlog.foodBlogRestApi.service.PostService;
import com.foodBlog.foodBlogRestApi.service.serviceImpl.PostServiceImpl;
import com.foodBlog.foodBlogRestApi.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create/post")
    public ResponseEntity<PostDto> makeAPost (@Valid @RequestBody PostDto postDto) {
        PostDto postDto1 = postService.createPost(postDto);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/fetch/posts")
    public ResponseEntity<List<PostDto>> retrieveMultiplePosts () {
        List<PostDto> postDtos = postService.getAllPosts();
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/get/page/posts")
    public ResponseEntity<List<PostDto>> multiPosts (@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize
                                                        ) {
        List<PostDto> dto = postService.pages(pageNo,pageSize);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping("/get-customResponse-page/posts")
    public ResponseEntity<PostPaginationResponse> customPage (@RequestParam(value = "pageNo",defaultValue =AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                              @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize){
        PostPaginationResponse response = postService.customisedPages(pageNo,pageSize);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get-sort/posts")
    public ResponseEntity<List<PostDto>> retrieveAndSortPost (@RequestParam String sortBy,
                                                              @RequestParam String sortDir) {
    List<PostDto> postDtos = postService.sortPost(sortBy,sortDir);
    return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/get-page-sort/posts")
    public ResponseEntity<List<PostDto>> retrievePageAndSort (@RequestParam int pageNo,
                                                              @RequestParam int pageSize,
                                                              @RequestParam String sortDir,
                                                              @RequestParam String sortBy){
      List<PostDto> postDtos = postService.pageAndSort(pageNo,pageSize,sortDir,sortBy);
      return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }


    @GetMapping("/fetch/post/{id}")
    public ResponseEntity<PostDto> retrivePostById (@PathVariable("id") long id) {
       PostDto postDto = postService.getPostById(id);
       return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @PutMapping("/update/post")
    public ResponseEntity<PostDto> updatePost (@RequestParam(name = "id") long id, @Valid @RequestBody PostDto postDto) {
       PostDto dto =  postService.updateAPost(id,postDto);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @DeleteMapping("/delete/post/{id}")
    public ResponseEntity<String> deletePost (@PathVariable(name ="id") Long id) {
        postService.deletePost(id);
        String message = "Post is deleted";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
