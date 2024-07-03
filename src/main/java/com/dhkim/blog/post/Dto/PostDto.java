package com.dhkim.blog.post.Dto;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
}