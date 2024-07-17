package com.dhkim.blog.post.dto;

import com.dhkim.blog.post.domain.Image;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int page = 0;
    private List<String> deleteImages;
    private List<Image> postImages;
}