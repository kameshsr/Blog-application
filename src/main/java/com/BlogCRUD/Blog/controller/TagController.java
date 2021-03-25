package com.BlogCRUD.Blog.controller;

import com.BlogCRUD.Blog.model.Post;
import com.BlogCRUD.Blog.model.Tag;
import com.BlogCRUD.Blog.repository.TagRepository;
import com.BlogCRUD.Blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TagController {
    @Autowired
    private PostService postsService;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/posts/saveTag/{id}")
    public String saveTags(@PathVariable("id") int postId, @ModelAttribute("tags") Tag tags) {
        Post currentPosts = postsService.getPostsById(postId);
        currentPosts.getTags().add(tags);
        tagRepository.save(tags);
        return "redirect:/posts/listUnPublishedPosts";
    }

    @GetMapping("/posts/addTag/{id}")
    public String addTag(@PathVariable("id") int postId, Model model) {
        model.addAttribute("tag", tagRepository.findAll());
        model.addAttribute("posts", postsService.findOne(postId));
        Tag tags = new Tag();
        model.addAttribute("tags", tags);
        return "AddPostTag";
    }
}
