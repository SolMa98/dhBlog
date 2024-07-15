package com.dhkim.blog.login.repository;

import com.dhkim.blog.login.domain.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.accessToken = :newAccessToken WHERE t.id = :id")
    void updateAccessTokenById(String id, String newAccessToken);
}