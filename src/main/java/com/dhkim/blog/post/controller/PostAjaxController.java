package com.dhkim.blog.post.controller;

import com.dhkim.blog.post.dto.PostDto;
import com.dhkim.blog.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ajax/post")
public class PostAjaxController {
    private final PostService service;
    @PostMapping("/create")
    public String postCreate(PostDto postDto, List<MultipartFile> images){
        return service.savePost(postDto, images);
    }

    @PutMapping("/update")
    public String postUpdate(PostDto postDto, List<MultipartFile> images){
        return service.updatePost(postDto, images);
    }

    @DeleteMapping()
    public String postDelete(HttpServletRequest request, String id){
        return service.postDelete(request, id);
    }
}
