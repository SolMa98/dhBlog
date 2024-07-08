package com.dhkim.blog.login.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
    @Override
    public String loginPageOpen(HttpServletRequest request){
        return "/login/login";
    }

    @Override
    public String createAccountPageOpen(HttpServletRequest request){
        return "/login/accountCreate";
    }
}
