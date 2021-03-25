package com.BlogCRUD.Blog.service;

import com.BlogCRUD.Blog.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPublishedPosts();

    List<Post> getAllUnPublishedPosts();

    void savePosts(Post posts);

    Post getPostsById(int id);

    void deletePostsById(int id);

    Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    List<Post> listAll(String keyword);

    Optional<Post> findOne(int studentId);
}
