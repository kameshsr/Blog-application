package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    void saveTags(Tag tags);
}
