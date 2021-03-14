package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Post;
import com.BlogCRUD.Blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postsRepository;

    @Override
    public List <Post> getAllPublishedPosts(){
        return postsRepository.findByisPublished(true);
    }

    @Override
    public List<Post> getAllUnPublishedPosts() {
        return postsRepository.findByisPublished(false);
    }

    @Override
    public void savePosts(Post posts) {
        this.postsRepository.save(posts);
    }

    @Override
    public Post getPostsById(int id) {
        Optional <Post> optional = postsRepository.findById(id);
        Post posts = null;
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
