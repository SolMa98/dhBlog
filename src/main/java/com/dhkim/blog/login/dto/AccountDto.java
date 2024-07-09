package com.dhkim.blog.login.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String id;
    private String password;
    private String nickname;
    private String name;
    private String gender;
}