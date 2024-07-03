package com.dhkim.blog.post.controller;

import com.dhkim.blog.post.Dto.PostDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax/post")
public class PostAjaxController {
    @PostMapping("/create")
    public String postCreate(HttpServletRequest request, PostDto postDto){
        System.out.println(postDto);
        return "";
    }
}
