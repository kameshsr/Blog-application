package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Comment;
import com.BlogCRUD.Blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findByPostId(int postId) {
        return this.commentRepository.findByPostId(postId);
    }

    @Override
    public Optional<Comment> findByIdAndPostId(int id, int postId) {
        return this.commentRepository.findByIdAndPostId(id, postId);
    }
}
