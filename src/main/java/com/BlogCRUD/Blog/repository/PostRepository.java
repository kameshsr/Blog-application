package com.BlogCRUD.Blog.repository;

import com.BlogCRUD.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findByisPublished(boolean isPublished);

    @Query("SELECT p FROM Post p WHERE CONCAT(p.title, ' ', p.tag, ' ', p.content, ' ', p.author) LIKE %?1%")
    List<Post> search(String keyword);

    @Query("SELECT DISTINCT (p.author) from Post p")
    List<String> findByDistinctAuthor();

    @Query("SELECT DISTINCT (p.tag) from Post p")
    List<String> findByDistinctTag();

    List<Post> findByAuthorAndPublishedAt(String author, LocalDateTime published_at);

    Post findByauthor(String author);

    List<Post> findByauthorIn(List<String> author);

    List<Post> findBytagIn(List<String> tag);

    List<Post> findBypublishedAt(LocalDateTime published_at);




}
