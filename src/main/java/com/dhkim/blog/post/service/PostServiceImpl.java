package com.dhkim.blog.post.service;

import com.dhkim.blog.login.service.LoginService;
import com.dhkim.blog.post.domain.Image;
import com.dhkim.blog.post.domain.Post;
import com.dhkim.blog.post.domain.Reply;
import com.dhkim.blog.post.dto.PostDto;
import com.dhkim.blog.post.dto.ReplyDto;
import com.dhkim.blog.post.repository.ImageRepository;
import com.dhkim.blog.post.repository.PostRepository;
import com.dhkim.blog.post.repository.ReplyRepository;
import com.dhkim.blog.util.ImageUtils;
import com.dhkim.blog.util.PageUtils;
import com.dhkim.blog.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final ReplyRepository replyRepository;
    private final LoginService loginService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ImageRepository imageRepository, ReplyRepository replyRepository, LoginService loginService){
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.replyRepository = replyRepository;
        this.loginService = loginService;
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
    public String savePost(PostDto postDto, List<MultipartFile> images) {
        try {
            Post post = new Post();
            post.setTitle(postDto.getTitle());
            post.setWriter(postDto.getWriter());
            post.setContent(postDto.getContent());
            Post savePost = postRepository.save(post);

            if (images != null) {
                for (MultipartFile image : images) {
                    ImageUtils imageUtils = ImageUtils.saveImgFile(image);
                    Image postImg = new Image();
                    postImg.setOriginalFileName(imageUtils.getOriginalFileName());
                    postImg.setFileName(imageUtils.getFileName());
                    postImg.setFilePath(imageUtils.getFilePath());
                    postImg.setPost(savePost);

                    imageRepository.save(postImg);
                }
            }

            return "success";
        }catch (Exception e) {
            log.error(e.getMessage());
            return "Error";
        }
    }

    @Override
    public String updatePost(PostDto postDto, List<MultipartFile> images){
        try {
            Optional<Post> optionalPost = postRepository.findById(postDto.getId().toString());
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                post.setTitle(postDto.getTitle());
                post.setWriter(postDto.getWriter());
                post.setContent(postDto.getContent());
                Post savePost = postRepository.save(post);

                // 이미지 삭제
                if(postDto.getDeleteImages() != null){
                    for(String deleteImgId : postDto.getDeleteImages()){
                        imageRepository.deleteById(deleteImgId);
                    }
                }

                // 새로운 이미지 추가
                if (images != null) {
                    for (MultipartFile image : images) {
                        ImageUtils imageUtils = ImageUtils.saveImgFile(image);
                        Image postImg = new Image();
                        postImg.setOriginalFileName(imageUtils.getOriginalFileName());
                        postImg.setFileName(imageUtils.getFileName());
                        postImg.setFilePath(imageUtils.getFilePath());
                        postImg.setPost(savePost);

                        imageRepository.save(postImg);
                    }
                }
            }

            return "success";
        }catch (Exception e) {
            log.error(e.getMessage());
            return "Error";
        }
    }

    @Override
    public String postListPageOpen(HttpServletRequest request, PostDto post) {
        Pageable pageable = PageRequest.of(post.getPage(), 10, Sort.by("createdAt").descending());
        Page<Post> postData;
        if ((post.getTitle() != null && !post.getTitle().isEmpty()) && (post.getContent() != null && !post.getContent().isEmpty())) {
            postData = postRepository.findByTitleContainingOrContentContaining(post.getTitle(), post.getContent(), pageable);
        } else if (post.getTitle() != null && !post.getTitle().isEmpty()) {
            postData = postRepository.findByTitleContaining(post.getTitle(), pageable);
        } else if (post.getContent() != null && !post.getContent().isEmpty()) {
            postData = postRepository.findByContentContaining(post.getContent(), pageable);
        } else if (post.getWriter() != null && !post.getWriter().isEmpty()) {
            postData = postRepository.findByWriter(post.getWriter(), pageable);
        }else{
            postData = postRepository.findAll(pageable);
        }
        PaginationUtils pagination = PaginationUtils.getPagination(postData.getSize(), postData.getNumber(), postData.getTotalElements(), postData.getTotalPages());

        request.setAttribute("data", postData);
        request.setAttribute("page", pagination);
        request.setAttribute("param", post);
        PageUtils.pageHeaderTabSetting(request, loginService.jwtTokenValidation(request));

        return "/post/postListPage";
    }

    @Override
    public String postPageOpen(HttpServletRequest request, String id){
        Post data = getPostById(id);
        request.setAttribute("post", data);
        PageUtils.pageHeaderTabSetting(request, loginService.jwtTokenValidation(request));
        HttpSession session = request.getSession();

        // 게시글 수정, 삭제 용
        if(data.getWriter().equals(session.getAttribute("userNickname"))){
            request.setAttribute("token", session.getAttribute("accessToken"));
            request.setAttribute("postAuthorityBtn", "ok");
        }else{
            request.setAttribute("token", "");
            request.setAttribute("postAuthorityBtn", "no");
        }

        // 댓글 용
        if(session.getAttribute("userNickname") != null && session.getAttribute("userNickname") != ""){
            request.setAttribute("reply_token", session.getAttribute("accessToken"));
            request.setAttribute("userNickName", session.getAttribute("userNickname"));
        }else{
            request.setAttribute("reply_token", "");
            request.setAttribute("userNickName", "");
        }

        return "/post/postPage";
    }

    @Override
    public String postCUPageOpen(HttpServletRequest request, String id) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            return "redirect:/post/list";
        }else{
            PageUtils.pageHeaderTabSetting(request, loginService.jwtTokenValidation(request));
            request.setAttribute("token", session.getAttribute("accessToken"));

            if(id != null){
                Post data = getPostById(id);
                request.setAttribute("postId", data.getId());
                request.setAttribute("postTitle", data.getTitle());
                request.setAttribute("postContent", data.getContent());
                request.setAttribute("postImages", data.getPostImages());
            }else{
                request.setAttribute("postId", "");
                request.setAttribute("postTitle", "");
                request.setAttribute("postContent", "");
                request.setAttribute("postImages", "");
            }
            
            return "/post/postCUPage";
        }
    }

    @Override
    public String postDelete(HttpServletRequest request, String id){
        try{
            postRepository.deleteById(id);
        }catch (Exception e){
            return "error";
        }

        return "ok";
    }

    @Override
    public String replyCreate(ReplyDto replyDto){
        try{
            Optional<Post> optionalPost = postRepository.findById(replyDto.getPost_id());
            if (optionalPost.isPresent()) {
                Reply reply = new Reply();
                reply.setPost(optionalPost.get());
                reply.setComment(replyDto.getComment());
                reply.setNickname(replyDto.getNickname());

                Reply resultReply = replyRepository.save(reply);
                return resultReply.getId().toString();
            } else {
                return "error";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String replyDelete(HttpServletRequest request, String id) {
        try{
            replyRepository.deleteById(id);
        }catch (Exception e){
            return "error";
        }
        return "ok";
    }
}
