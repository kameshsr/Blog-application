package com.BlogCRUD.Blog.repository;

import com.BlogCRUD.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByisPublished(boolean isPublished);
    @Query("SELECT p FROM Post p WHERE CONCAT(p.title, ' ', p.tag, ' ', p.content, ' ', p.author) LIKE %?1%")
    public List<Post> search(String keyword);
}
