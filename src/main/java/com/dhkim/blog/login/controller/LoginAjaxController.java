package com.dhkim.blog.login.controller;

import com.dhkim.blog.login.domain.JwtToken;
import com.dhkim.blog.login.dto.AccountDto;
import com.dhkim.blog.login.dto.SingInDto;
import com.dhkim.blog.login.jwt.JwtTokenProvider;
import com.dhkim.blog.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    public JwtToken signIn(@RequestBody SingInDto singIn){
        JwtToken jwtToken = service.signIn(singIn.getId(), singIn.getPassword());

        log.info("request username = {}, password = {}", singIn.getId(), singIn.getPassword());
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        System.out.printf("request username = %s, password = %s\n", singIn.getId(), singIn.getPassword());
        System.out.printf("jwtToken accessToken = %s, refreshToken = %s\n", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken;
    }
}