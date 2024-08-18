package com.dhkim.blog.post.repository;

import com.dhkim.blog.post.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, String> {
}
