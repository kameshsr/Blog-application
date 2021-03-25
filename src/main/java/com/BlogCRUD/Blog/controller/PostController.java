package com.BlogCRUD.Blog.controller;

import com.BlogCRUD.Blog.model.Post;
import com.BlogCRUD.Blog.model.Tag;
import com.BlogCRUD.Blog.repository.CommentRepository;
import com.BlogCRUD.Blog.repository.PostRepository;
import com.BlogCRUD.Blog.repository.TagRepository;
import com.BlogCRUD.Blog.repository.UserRepository;
import com.BlogCRUD.Blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PostController {

    public String searchKeyword = null;

    @Autowired
    private PostService postsService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    Post newPost = new Post();
    Optional<String> tag1;
    Optional<String> author1;
    Optional<String> publishedDate;

    String startingDate, endingDate;

    @GetMapping("/posts/list")
    public String viewPostsList(Model model, @Param("keyword") String keyword, @Param("author")
            Optional<String> author, @Param("tag") Optional<String> tag
            , @Param("content") Optional<String> content, @Param("startDate") String startDate, @Param("endDate") String endDate) {
        System.out.println(startDate+" "+endDate);
        startingDate = startDate;
        endingDate = endDate;
        author1 = author;
        tag1 = tag;
        publishedDate = content;
        searchKeyword = keyword;
        List<Post> listPosts = postsService.listAll(keyword);
        model.addAttribute(("listPosts"), listPosts);
        model.addAttribute("tagList", tagRepository.findAll());
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

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable("id") int postId, Model model) {
        model.addAttribute("posts", postsService.getPostsById(postId));
        model.addAttribute("comments", commentRepository.findByPostId(postId));
        return "ViewPost";
    }

    @PostMapping("/posts/savePosts")
    public String savePosts(@ModelAttribute("posts") Post posts) {

        postsService.savePosts(posts);
        String tag = posts.getTag();
        String[] listTag = tag.split(",");
        for (String tags : listTag) {
            Tag tag1 = new Tag(tags);
            tagRepository.save(tag1);
            posts.getTags().add(tag1);
        }
        postsService.savePosts(posts);
        return "redirect:/posts/list";
    }

    @GetMapping("/posts/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
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

        Page<Post> page = postsService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Post> listPosts = page.getContent();


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("desc") ? "asc" : "desc");

        List<Post> listPost = new ArrayList<>();
        model.addAttribute("postList", listPosts);
        List<String> listAuthor = postRepository.findByDistinctAuthor();

        model.addAttribute("listAuthor", listAuthor);
        model.addAttribute("listTag", postRepository.findByDistinctTag());

        if (searchKeyword != null) {
            listPost = postsService.listAll(searchKeyword);
            model.addAttribute("listPost1", listPost);
        } else if (searchKeyword == null) {
            model.addAttribute("listPost1", listPosts);
        }
        if(!tag1.isEmpty() || startingDate!=null || !author1.isEmpty()) {
            List<Post> listPostFilter = new ArrayList<>();
            if (!tag1.isEmpty()) {
                List<String> tagList = Arrays.asList(tag1.stream().collect(Collectors.toList()).get(0).split(","));
                listPostFilter = postRepository.findBytagIn(tagList);
            }else if(startingDate!=null && endingDate!=null){
                List<Post> listPostDateFilter= new ArrayList<>();
                List<String> publishedDateList = new ArrayList<>();
                for(Post post: listPosts){
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    String date = post.getPublishedAt().format(formatter);
                    if(startingDate.compareTo(date) <= 0 && endingDate.compareTo(date) >= 0) {
                        listPostDateFilter.add(post);
                    }
                    publishedDateList.add(date.substring(0, 10));
                }
                listPostFilter = listPostDateFilter;
            }
            if (!author1.isEmpty()) {
                List<String> authorList = Arrays.asList(author1.stream().collect(Collectors.toList()).get(0).split(","));
                listPostFilter = postRepository.findByauthorIn(authorList);
            }
            model.addAttribute("listPost1", listPostFilter);
        }
        model.addAttribute("newPost", newPost);
        return "PostsList";
    }
}
