package com.foodBlog.foodBlogRestApi.repository;

import com.foodBlog.foodBlogRestApi.domain.Comment;
import com.foodBlog.foodBlogRestApi.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId (long id);


}
