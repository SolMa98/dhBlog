package com.dhkim.blog.login.repository;

import com.dhkim.blog.login.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {

}