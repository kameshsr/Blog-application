package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Post;
import com.BlogCRUD.Blog.models.Tag;
import org.springframework.data.domain.Page;

import java.util.List;


public interface PostService {
    List<Post> getAllPublishedPosts();
    List<Post> getAllUnPublishedPosts();
    void savePosts(Post posts);
    Post getPostsById(int id);
    void deletePostsById(int id);
    Page< Post > findPaginated(int pageNo, int pageSize);


    Post findOne(int studentId);
}
