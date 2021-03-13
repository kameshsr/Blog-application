package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Posts;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostsService {
    List<Posts> getAllPosts();
    void savePosts(Posts posts);
    Posts getPostsById(int id);
    void deletePostsById(int id);
}
