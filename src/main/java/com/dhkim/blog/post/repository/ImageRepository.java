package com.dhkim.blog.post.repository;

import com.dhkim.blog.post.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
