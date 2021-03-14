package com.BlogCRUD.Blog.controllers;

import com.BlogCRUD.Blog.models.Posts;
import com.BlogCRUD.Blog.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/list")
    public String viewHomePage(Model model) {
        model.addAttribute("listPosts", postsService.getAllPosts());
        return "PostsList";
    }

    @GetMapping("/showNewPostsForm")
    public String showNewPostsForm(Model model) {
        Posts posts = new Posts();
        model.addAttribute("posts", posts);
        return "NewPosts";
    }

    @PostMapping("/savePosts")
    public String savePosts(@ModelAttribute("posts") Posts posts) {
        postsService.savePosts(posts);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model){
        Posts posts = postsService.getPostsById(id);
        model.addAttribute("posts", posts);
        return "UpdatePosts";
    }

    @GetMapping("/deletePosts/{id}")
    public String deletePosts(@PathVariable(value = "id") int id) {
        this.postsService.deletePostsById(id);
        return "redirect:/";
    }


}
