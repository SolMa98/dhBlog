package com.dhkim.blog.util;

import com.dhkim.blog.login.dto.JwtDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
    public static void setTokenSession(HttpServletRequest request, JwtDto jwt){
        HttpSession session = request.getSession();
        session.setAttribute("access_token", jwt.getAccessToken());
        session.setAttribute("refresh_token", jwt.getRefreshToken());
        log.info("access_token " + session.getAttribute("access_token").toString());
        log.info("refresh_token " + session.getAttribute("refresh_token").toString());
    }
}
