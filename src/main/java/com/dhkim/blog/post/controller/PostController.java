package com.dhkim.blog.post.controller;

import com.dhkim.blog.post.dto.PostDto;
import com.dhkim.blog.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService service;

    @GetMapping("/list")
    public String postListPageOpen(HttpServletRequest request, PostDto post){
        return service.postListPageOpen(request, post);
    }

    @GetMapping("/page")
    public String postPageOpen(HttpServletRequest request, String id){
        return service.postPageOpen(request, id);
    }

    @GetMapping("/cu")
    public String postCreatePage(HttpServletRequest request, String id) {
        return service.postCUPageOpen(request, id);
    }
}