package com.dhkim.blog.login.controller;

import com.dhkim.blog.login.dto.AccountDto;
import com.dhkim.blog.login.dto.SingInDto;
import com.dhkim.blog.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
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

    @PostMapping("/ajax/sign-in")
    public String signIn(HttpServletRequest request, @RequestBody SingInDto singIn){
        return service.signIn(request, singIn.getId(), singIn.getPassword());
    }

    @PostMapping("/ajax/token/check")
    public Boolean tokenCheck(HttpServletRequest request){
        return service.jwtTokenValidation(request);
    }
}