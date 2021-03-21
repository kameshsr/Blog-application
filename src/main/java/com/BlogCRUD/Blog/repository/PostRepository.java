package com.BlogCRUD.Blog.repository;

import com.BlogCRUD.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> , JpaSpecificationExecutor<Post>{
    List<Post> findByisPublished(boolean isPublished);
    @Query("SELECT p FROM Post p WHERE CONCAT(p.title, ' ', p.tag, ' ', p.content, ' ', p.author) LIKE %?1%")
    public List<Post> search(String keyword);

    List<Post> findByAuthorAndPublishedAt(String author, LocalDateTime published_at);

    //@Query(nativeQuery = true, value = "SELECT * FROM posts as p WHERE p.author IN (:authors)")
    //List<Post> findByauthor(@Param("authors") List<String> authors);
    Post findByauthor(String author);
    List<Post> findBytagIn(List<String> tag);
    List<Post> findBypublishedAt(LocalDateTime published_at);
}
