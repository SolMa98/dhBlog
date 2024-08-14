package com.dhkim.blog.post.repository;

import com.dhkim.blog.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitleContaining(String title, Pageable pageable);
    Page<Post> findByContentContaining(String content, Pageable pageable);
    Page<Post> findByWriter(String writer, Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:title% OR p.content LIKE %:content%")
    Page<Post> findByTitleContainingOrContentContaining(@Param("title") String title, @Param("content") String content, Pageable pageable);
}
