package com.dhkim.blog.post.service;

import com.dhkim.blog.post.domain.Post;
import com.dhkim.blog.post.dto.PostDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    /**
     * 블로그 전체 글 가져오기
     * @return 블로그 전체 글 Object List
     */
    Page<Post> getAllPosts(Pageable pageable);

    /**
     * 블로그 글 저장하기
     * @param postDto
     * @return 성공 or 실패 여부
     */
    public Post savePost(PostDto postDto);

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
