package com.dhkim.blog.post.service;

import com.dhkim.blog.post.Domain.Post;
import com.dhkim.blog.post.Dto.PostDto;
import com.dhkim.blog.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post savePost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setWriter(postDto.getWriter());
        post.setContent(postDto.getContent());

        return postRepository.save(post);
    }

    @Override
    public String postListPageOpen(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("createdAt").descending());

        Page<Post> postData = postRepository.findAll(pageable);
        request.setAttribute("data", postData);

        return "/post/postListPage";
    }

    @Override
    public String postPageOpen(HttpServletRequest request, String id){
        return "/post/postPage";
    }

    @Override
    public String postCUPageOpen(HttpServletRequest request, String id) { return "/post/postCreate"; }
}
