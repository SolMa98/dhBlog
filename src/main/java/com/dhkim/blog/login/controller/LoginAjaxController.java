package com.dhkim.blog.login.controller;

import com.dhkim.blog.login.dto.AccountDto;
import com.dhkim.blog.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class LoginAjaxController {
    private final LoginService service;

    @GetMapping("/ajax/account/id/check")
    public Boolean isIdUnique(String id){
        return service.isIdUnique(id);
    }

    @GetMapping("/ajax/account/nickname/check")
    public Boolean isNicknameUnique(String nickname){
        return service.isNicknameUnique(nickname);
    }

    @PostMapping("/ajax/account")
    public ResponseEntity<String> accountCreate(HttpServletRequest request, AccountDto account){
        return service.saveAccount(request, account);
    }
}
