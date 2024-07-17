package com.dhkim.blog.post.service;

import com.dhkim.blog.post.domain.Post;
import com.dhkim.blog.post.dto.PostDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    /**
     * 블로그 전체 글 가져오기
     * @return 블로그 전체 글 Object List
     */
    public Page<Post> getAllPosts(Pageable pageable);

    /**
     * 블로그 id로 select해서 특정 글 가져오기
     * @param id
     * @return 가져온 블로그 글
     */
    public Post getPostById(String id);

    /**
     * 블로그 글 저장하기
     * @param postDto
     * @return 성공 or 실패 여부
     */
    public String savePost(PostDto postDto, List<MultipartFile> images);

    public String updatePost(PostDto postDto, List<MultipartFile> images);

    /**
     * 블로그 글 리스트 페이지
     * @param request
     * @return "/post/postPage"
     */
    public String postListPageOpen(HttpServletRequest request, PostDto post);

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

    public String postDelete(HttpServletRequest request, String id);
}
