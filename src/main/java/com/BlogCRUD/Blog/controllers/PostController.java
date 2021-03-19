package com.BlogCRUD.Blog.controllers;

import com.BlogCRUD.Blog.models.Comment;
import com.BlogCRUD.Blog.models.Post;
import com.BlogCRUD.Blog.models.Tag;
import com.BlogCRUD.Blog.models.User;
import com.BlogCRUD.Blog.repository.CommentRepository;
import com.BlogCRUD.Blog.repository.PostRepository;
import com.BlogCRUD.Blog.repository.TagRepository;
import com.BlogCRUD.Blog.repository.UserRepository;
import com.BlogCRUD.Blog.services.PostService;
import com.BlogCRUD.Blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.metrics.StartupStep;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/posts")
public class PostController {

    public String keyword1=null, filterKeyword;

    @Autowired
    private PostService postsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

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
    public String viewPostsList(Model model, @Param("keyword") String keyword, @Param("keyword2") String keyword2) {
        keyword1=keyword;
        filterKeyword =keyword2;
        System.out.println(keyword2);
        List<Post> listPosts = postsService.listAll(keyword);
        model.addAttribute(("listPosts"), listPosts);
        String authorName = null;
        model.addAttribute("authorName", authorName);
        return findPaginated(1, "publishedAt", "asc", model);
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
        model.addAttribute("comments", commentRepository.findByPostId(postId));

        return "ViewPost";
    }
    @RequestMapping("/posts/addComment/{postsId}/comment")
    public String addComments(@PathVariable("postsId")int postId, @ModelAttribute("newComment") Comment newComment) {

        Post currentPosts = postsService.getPostsById(postId);
        newComment.setPost(currentPosts);
        commentRepository.save(newComment);

        currentPosts.getComments().add(newComment);

        postsService.savePosts(currentPosts);
        //model.addAttribute("posts", postsService.getPostsById(postId));
        return "redirect:/posts/{postsId}";
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
        return "redirect:/posts/list";
    }

    @GetMapping("/posts/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Page < Post > page = postsService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List < Post > listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("desc") ? "asc" : "desc");

        List<Post> listPosts1;
        if(keyword1!=null){
            listPosts1 = postsService.listAll(keyword1);
            model.addAttribute("listPost1", listPosts1);
        }

        if(keyword1==null){
            model.addAttribute("listPost1", listPosts);

        }
        if(filterKeyword!=null){
            String[] arguments=filterKeyword.split(":");

            //2021-03-17T22:37:13
            String pattern = "yyyy-MM-dd HH:mm:ss";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(arguments[1]));

            List<Post> filterList=postRepository.findByAuthorAndPublishedAt(arguments[0], localDateTime);

            model.addAttribute("listPost1", filterList);
        }
        String authorName=null;
        model.addAttribute("authorName", authorName);
        return "PostsList";
    }

    @RequestMapping(value = "posts/addComment/{id}", method = RequestMethod.GET)
    public String addComment(@PathVariable("id") int postsId, Model model){
        model.addAttribute("comment", commentRepository.findAll());

        Comment newComment = new Comment();
        model.addAttribute("newComment", newComment);
        model.addAttribute("posts", postsService.getPostsById(postsId));
        return "AddComment";
    }

    @RequestMapping(value = "/posts/{postsId}/updateComments/{commentId}", method = RequestMethod.GET)
    public String updateComment(@PathVariable("postsId") int postsId,
                                @PathVariable("commentId") int commentId ,Model model){
        model.addAttribute("posts", postsService.getPostsById(postsId));
        model.addAttribute("newComment", commentRepository.findById(commentId));
        return "AddComment";
    }

    @RequestMapping(value = "/posts/{postsId}/deleteComments/{commentId}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable("postsId") int postsId,
                                @PathVariable("commentId") int commentId ,Model model){
        Optional<Comment> comment = commentRepository.findById(commentId);
        this.commentRepository.deleteById(commentId);

        return "redirect:/posts/{postsId}";
    }
}
