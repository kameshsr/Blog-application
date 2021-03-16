package com.BlogCRUD.Blog.controllers;

import com.BlogCRUD.Blog.models.Post;
import com.BlogCRUD.Blog.models.Tag;
import com.BlogCRUD.Blog.models.User;
import com.BlogCRUD.Blog.repository.TagRepository;
import com.BlogCRUD.Blog.repository.UserRepository;
import com.BlogCRUD.Blog.services.PostService;
import com.BlogCRUD.Blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.metrics.StartupStep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signupForm";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {

        userRepository.save(user);

        return "RegisterSuccess";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/processLogin")
    public String processLogin(User user){
        if(user.getEmail().equals("admin1@gmail.com") && user.getPassword().equals("admin1")){
            return "redirect:/posts/list";
        }
        return "login";
    }

    @GetMapping("/posts/list")
    public String viewPostsList(Model model) {
        //model.addAttribute("listPosts", postsService.getAllPublishedPosts());
        return findPaginated(1, model);
    }

    @GetMapping("/listUnPublishedPosts")
    public String listUnPublishedPosts(Model model) {
        model.addAttribute("listPosts", postsService.getAllUnPublishedPosts());
        return "UnPublishedPosts";

    }

    @GetMapping("/posts/showNewPostsForm")
    public String showNewPostsForm(Model model) {
        Post posts = new Post();
        model.addAttribute("posts", posts);
        return "NewPosts";
    }

    @GetMapping("/posts/addTag/{id}")
    public String addTag(@PathVariable("id") int postId, Model model){
        model.addAttribute("tag", tagService.findAll());
        model.addAttribute("posts", postsService.findOne(postId));
        Tag tags = new Tag();
        model.addAttribute("tags", tags);
        return "AddPostTag";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable("id")int postId, Model model){
        model.addAttribute("posts", postsService.getPostsById(postId));
        return "ViewPost";
    }


    @PostMapping("/posts/savePosts")
    public String savePosts(@ModelAttribute("posts") Post posts) {
        postsService.savePosts(posts);

        String tag = posts.getTag();
        String[] listTag = tag.split(",");

        for(String tags:listTag){
            Tag tag1 = new Tag(tags);
            tagService.saveTags(tag1);

            posts.getTags().add(tag1);
        }


        postsService.savePosts(posts);

        return "redirect:/posts/list";
    }

    @PostMapping("/posts/saveTag/{id}")
    public String saveTags(@PathVariable("id") int postId, @ModelAttribute("tags") Tag tags) {
        Post currentPosts = postsService.getPostsById(postId);
        currentPosts.getTags().add(tags);
        tagService.saveTags(tags);

        return "redirect:/posts/listUnPublishedPosts";
    }

    @GetMapping("/posts/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model){
        Post posts = postsService.getPostsById(id);
        model.addAttribute("posts", posts);
        return "UpdatePosts";
    }

    @GetMapping("/posts/deletePosts/{id}")
    public String deletePosts(@PathVariable(value = "id") int id) {
        this.postsService.deletePostsById(id);
        return "redirect:/";
    }

    @GetMapping("/posts/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;

        Page< Post > page = postsService.findPaginated(pageNo, pageSize);
        List< Post > listPost = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listPost", listPost);
        return "PostsList";
    }


}
