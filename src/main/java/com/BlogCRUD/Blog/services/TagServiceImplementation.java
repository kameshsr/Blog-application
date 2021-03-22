package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Tag;
import com.BlogCRUD.Blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagServiceImplementation implements TagService{

    @Autowired
    private TagRepository tagRepository;

    public TagServiceImplementation(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void saveTags(Tag tags) {
        tagRepository.save(tags);
    }
}
