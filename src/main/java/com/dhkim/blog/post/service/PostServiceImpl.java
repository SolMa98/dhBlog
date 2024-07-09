package com.dhkim.blog.post.service;

import com.dhkim.blog.post.domain.Image;
import com.dhkim.blog.post.domain.Post;
import com.dhkim.blog.post.dto.PostDto;
import com.dhkim.blog.post.repository.ImageRepository;
import com.dhkim.blog.post.repository.PostRepository;
import com.dhkim.blog.util.ImageUtils;
import com.dhkim.blog.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ImageRepository imageRepository){
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> savePost(PostDto postDto, List<MultipartFile> images) {
        try {
            Post post = new Post();
            post.setTitle(postDto.getTitle());
            post.setWriter(postDto.getWriter());
            post.setContent(postDto.getContent());

            Post savePost = postRepository.save(post);

            for (MultipartFile image : images) {
                ImageUtils imageUtils = ImageUtils.saveImgFile(image);
                Image postImg = new Image();
                postImg.setOriginalFileName(imageUtils.getOriginalFileName());
                postImg.setFileName(imageUtils.getFileName());
                postImg.setFilePath(imageUtils.getFilePath());
                postImg.setPost(savePost);

                imageRepository.save(postImg);
            }

            return ResponseEntity.ok("success");
        }catch (Exception e) {
            // 오류 발생 시 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images");
        }
    }

    @Override
    public String postListPageOpen(HttpServletRequest request, PostDto post) {
        Pageable pageable = PageRequest.of(post.getPage(), 10, Sort.by("createdAt").descending());
        Page<Post> postData = postRepository.findAll(pageable);
        PaginationUtils pagination = PaginationUtils.getPagination(postData.getSize(), postData.getNumber(), postData.getTotalElements(), postData.getTotalPages());

        request.setAttribute("data", postData);
        request.setAttribute("page", pagination);

        return "/post/postListPage";
    }

    @Override
    public String postPageOpen(HttpServletRequest request, String id){
        Post data = getPostById(id);
        request.setAttribute("post", data);

        return "/post/postPage";
    }

    @Override
    public String postCUPageOpen(HttpServletRequest request, String id) { return "/post/postCreate"; }
}
