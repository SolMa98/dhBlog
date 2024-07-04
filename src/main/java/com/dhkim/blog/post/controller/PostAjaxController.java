package com.dhkim.blog.post.controller;

import com.dhkim.blog.post.dto.PostDto;
import com.dhkim.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ajax/post")
public class PostAjaxController {
    private final PostService service;
    @PostMapping("/create")
    public String postCreate(PostDto postDto){
        try{
            service.savePost(postDto);

            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
