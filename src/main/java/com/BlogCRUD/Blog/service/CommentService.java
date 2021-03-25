package com.BlogCRUD.Blog.service;

import com.BlogCRUD.Blog.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findByPostId(int postId);

    Optional<Comment> findByIdAndPostId(int id, int postId);
}
