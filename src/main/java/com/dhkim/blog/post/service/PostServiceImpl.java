package com.dhkim.blog.post.service;

import com.dhkim.blog.post.Domain.Post;
import com.dhkim.blog.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public String postListPageOpen(HttpServletRequest request) {
        System.out.println(getAllPosts());

        return "/post/postListPage";
    }

    @Override
    public String postPageOpen(HttpServletRequest request, String id){
        return "/post/postPage";
    }

    @Override
    public String postCUPageOpen(HttpServletRequest request, String id) { return "/post/postCreate"; }
}
