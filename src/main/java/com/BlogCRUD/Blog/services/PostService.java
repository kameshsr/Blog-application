package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Post;
import com.BlogCRUD.Blog.models.Tag;

import java.util.List;


public interface PostService {
    List<Post> getAllPublishedPosts();
    List<Post> getAllUnPublishedPosts();
    void savePosts(Post posts);
    Post getPostsById(int id);
    void deletePostsById(int id);



    Post findOne(int studentId);
}
