package com.dhkim.blog.login.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Token {
    @Id
    private String id;
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
