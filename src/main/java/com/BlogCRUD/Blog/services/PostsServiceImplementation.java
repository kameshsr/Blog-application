package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Posts;
import com.BlogCRUD.Blog.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsServiceImplementation implements PostsService{

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public List < Posts > getAllPosts(){
        return postsRepository.findAll();
    }

    @Override
    public void savePosts(Posts posts) {
        this.postsRepository.save(posts);
    }

    @Override
    public Posts getPostsById(int id) {
        Optional < Posts > optional = postsRepository.findById(id);
        Posts posts = null;
        if (optional.isPresent()) {
            posts = optional.get();
        } else {
            throw new RuntimeException("Posts not found for id: "+id);
        }
        return posts;
    }

    @Override
    public void deletePostsById(int id) {
        this.postsRepository.deleteById(id);
    }
}
