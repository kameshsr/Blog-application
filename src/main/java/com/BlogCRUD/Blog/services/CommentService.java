package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findByPostId(int postId);

    Optional<Comment> findByIdAndPostId(int id, int postId);
}
