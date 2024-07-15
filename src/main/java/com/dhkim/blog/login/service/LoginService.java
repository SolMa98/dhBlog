package com.dhkim.blog.login.service;

import com.dhkim.blog.login.domain.JwtToken;
import com.dhkim.blog.login.dto.AccountDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    /**
     * 로그인 페이지 오픈
     * @param request
     * @return 로그인 페이지
     */
    public String loginPageOpen(HttpServletRequest request);

    /**
     * 회원 가입 페이지 오픈
     * @param request
     * @return 회원 가입 페이지
     */
    public String createAccountPageOpen(HttpServletRequest request);

    /**
     * 아이디 중복 검사
     * @param id
     * @return 중복 검사 결과
     */
    public boolean isIdUnique(String id);

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return 중복 검사 결과
     */
    public boolean isNicknameUnique(String nickname);

    /**
     * 계정 생성
     * @param request, account
     * @return 생성 결과
     */
    public ResponseEntity<String> saveAccount(HttpServletRequest request, AccountDto accountDto);

    /**
     * 로그인
     * @param id 아이디
     * @param password 비밀번호
     * @return 로그인 성공 여부
     */
    public String signIn(HttpServletRequest request, String id, String password);

    public Boolean jwtTokenValidation(HttpServletRequest request);
}
