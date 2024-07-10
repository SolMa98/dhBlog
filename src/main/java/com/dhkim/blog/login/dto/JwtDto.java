package com.dhkim.blog.login.dto;

import lombok.Data;

@Data
public class JwtDto {
    String accessToken;
    String refreshToken;
}
