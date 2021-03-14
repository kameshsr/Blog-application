package com.BlogCRUD.Blog.repository;

import com.BlogCRUD.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByisPublished(boolean isPublished);
}
