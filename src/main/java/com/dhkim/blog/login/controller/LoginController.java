package com.dhkim.blog.login.controller;

import com.dhkim.blog.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService service;

    @GetMapping()
    public String loginPageOpen(HttpServletRequest request){
        return service.loginPageOpen(request);
    }
}
