package com.dhkim.blog.post.service;

import com.dhkim.blog.post.domain.Post;
import com.dhkim.blog.post.dto.PostDto;
import com.dhkim.blog.post.repository.PostRepository;
import com.dhkim.blog.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post savePost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setWriter(postDto.getWriter());
        post.setContent(postDto.getContent());

        return postRepository.save(post);
    }

    @Override
    public String postListPageOpen(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Post> postData = postRepository.findAll(pageable);
        PaginationUtils pagination = PaginationUtils.getPagination(postData.getSize(), postData.getNumber(), postData.getTotalElements(), postData.getTotalPages());

        request.setAttribute("data", postData);
        request.setAttribute("page", pagination);

        return "/post/postListPage";
    }

    @Override
    public String postPageOpen(HttpServletRequest request, String id){
        return "/post/postPage";
    }

    @Override
    public String postCUPageOpen(HttpServletRequest request, String id) { return "/post/postCreate"; }
}
