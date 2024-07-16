package com.dhkim.blog.login.controller;

import com.dhkim.blog.login.service.LoginService;
import com.dhkim.blog.util.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginController {
    private final LoginService service;

    @GetMapping("login")
    public String loginPageOpen(HttpServletRequest request){
        return service.loginPageOpen(request);
    }

    @GetMapping("account/create")
    public String createAccountPageOpen(HttpServletRequest request){
        return service.createAccountPageOpen(request);
    }
}
