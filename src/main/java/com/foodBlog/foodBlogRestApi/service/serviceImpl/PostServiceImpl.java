package com.foodBlog.foodBlogRestApi.service.serviceImpl;

import com.foodBlog.foodBlogRestApi.domain.Comment;
import com.foodBlog.foodBlogRestApi.domain.Post;
import com.foodBlog.foodBlogRestApi.payload.CommentDto;
import com.foodBlog.foodBlogRestApi.payload.PostDto;
import com.foodBlog.foodBlogRestApi.exception.ResourceNotFoundException;
import com.foodBlog.foodBlogRestApi.payload.PostPaginationResponse;
import com.foodBlog.foodBlogRestApi.repository.PostRepository;
import com.foodBlog.foodBlogRestApi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Convert DTO to entity using ModelMapper
        Post post = mapper.map(postDto, Post.class);
        Post savedPost = postRepository.save(post);

        // Convert saved entity back to DTO using ModelMapper
        PostDto savedPostDto = mapper.map(savedPost, PostDto.class);
        return savedPostDto;
    }


    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos1 = posts.stream()
                .map(post -> mapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDtos1;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapper.map(post,PostDto.class);
    }

    @Override
    public PostDto updateAPost(long id,PostDto postDto) {
       Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
       Post savedPost = postRepository.save(post);
       PostDto dto = mapper.map(savedPost,PostDto.class);
        return dto;
    }

    @Override
    public void deletePost(Long id) {
       Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
       postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> pages(int pageNo, int pageSize) {
       Page<Post> page =  postRepository.findAll(PageRequest.of(pageNo,pageSize));
       List<Post> posts = page.getContent();
       List<PostDto> dtos = posts.stream().map((post)-> mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return dtos;
    }
@Override
//customised pagination API response
    public PostPaginationResponse customisedPages(int pageNo, int pageSize) {
        Page<Post> page =  postRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<Post> posts = page.getContent();
        List<PostDto> dtos = posts.stream().map((post)-> mapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostPaginationResponse postPaginationResponse = new PostPaginationResponse();
        postPaginationResponse.setContent(page.getContent());
        postPaginationResponse.setPageNo(page.getNumber());
        postPaginationResponse.setPageSize(page.getSize());
        postPaginationResponse.setTotalElements(page.getTotalElements());
        postPaginationResponse.setFirst(page.isFirst());
        postPaginationResponse.setLast(page.isLast());
        postPaginationResponse.setTotalPages(page.getTotalPages());
        return postPaginationResponse;
    }

    @Override
    public List<PostDto> sortPost(String sortBy, String sortDir) {
        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.fromString(sortDir),sortBy));
        postList.stream().forEach((c)-> System.out.println(c));
        List<PostDto> postDtos = new ArrayList<>();
        List<PostDto> postDtos1 = postList.stream().map((post)->mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos1;
    }


    @Override
    public List<PostDto> pageAndSort (int pageNo,int pageSize,String sortDir,String sortBy){
     Page<Post> pg = postRepository.findAll(PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.fromString(sortDir),sortBy)));
     List<Post> posts = pg.getContent();
     List<PostDto> postDtos = posts.stream().map((post)->mapper.map(post,PostDto.class)).collect(Collectors.toList());
     //List<PostDto> dtos = posts.stream().map((c)-> new PostDto(c.getId(),c.getTitle(),c.getDescription(),c.getContent())).collect(Collectors.toList());
     postDtos.stream().forEach((c)-> System.out.println(c));
     return postDtos;
    }


}
