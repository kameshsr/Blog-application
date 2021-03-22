package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Tag;
import com.BlogCRUD.Blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImplementation implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return this.tagRepository.findAll();
    }
}
