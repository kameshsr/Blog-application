package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Comment;
import com.BlogCRUD.Blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CommentServiceImplementation implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    public CommentServiceImplementation(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Optional<Comment> findByIdAndPostId(int id, int postId) {
        return commentRepository.findByIdAndPostId(id, postId);
    }
}
