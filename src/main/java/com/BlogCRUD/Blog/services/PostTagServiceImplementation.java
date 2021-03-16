package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.models.Post;
import com.BlogCRUD.Blog.models.Tag;
import com.BlogCRUD.Blog.repository.PostRepository;
import com.BlogCRUD.Blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostTagServiceImplementation implements PostService, TagService{

    @Autowired
    private PostRepository postsRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List <Post> getAllPublishedPosts(){
        return postsRepository.findByisPublished(true);
    }

    @Override
    public List<Post> getAllUnPublishedPosts() {
        return postsRepository.findByisPublished(false);
    }

    @Override
    public void savePosts(Post posts) {
        this.postsRepository.save(posts);
    }

    @Override
    public Post getPostsById(int id) {
        Optional <Post> optional = postsRepository.findById(id);
        Post posts = null;
        if (optional.isPresent()) {
            posts = optional.get();
        } else {
            throw new RuntimeException("Posts not found for id: "+id);
        }
        return posts;
    }

    @Override
    public void deletePostsById(int id) {
        this.postsRepository.deleteById(id);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postsRepository.findAll(pageable);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void saveTags(Tag tags) {
        this.tagRepository.save(tags);
    }

    @Override
    public Post findOne(int studentId) {
        return null;
    }
}
