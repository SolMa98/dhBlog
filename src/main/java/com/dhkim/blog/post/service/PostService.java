package com.dhkim.blog.post.service;

import com.dhkim.blog.post.Domain.Post;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    /**
     * 블로그 글 리스트 페이지
     * @param request
     * @return "/post/postPage"
     */
    public String postListPageOpen(HttpServletRequest request);

    /**
     * 블로그 게시글 페이지
     * @param request
     * @param id
     * @return
     */
    public String postPageOpen(HttpServletRequest request, String id);

    /**
     * 블로그 게시글 작성, 수정 페이지
     * @param request
     * @param id
     * @return
     */
    public String postCUPageOpen(HttpServletRequest request, String id);
}
