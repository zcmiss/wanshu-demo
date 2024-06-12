package com.wanshu.wanshu.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 工具类
 */
public class WebUtils {

    /**
     * 对密码做加密处理
     * @param password
     * @return
     */
    public static String passwordEncoder(String password){
        return new BCryptPasswordEncoder().encode(password);
    }


    public static String loginUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
