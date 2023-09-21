package com.foodBlog.foodBlogRestApi.repository;

import com.foodBlog.foodBlogRestApi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
