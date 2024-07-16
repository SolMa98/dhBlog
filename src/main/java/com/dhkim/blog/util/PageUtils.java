package com.dhkim.blog.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class PageUtils {
    public static void pageHeaderTabSetting(HttpServletRequest request, boolean use){
        if(use){
            HttpSession session = request.getSession();
            request.setAttribute("user", session.getAttribute("userId").toString());
            request.setAttribute("user_nickname", session.getAttribute("userNickname").toString());
        }else{
            request.setAttribute("user", "");
            request.setAttribute("user_nickname", "");
        }
    }
}
