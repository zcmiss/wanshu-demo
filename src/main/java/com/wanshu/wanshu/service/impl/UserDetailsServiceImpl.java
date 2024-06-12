package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号认证的Service
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        if(StringUtils.isNotBlank(username)){
            User user = new User();
            user.setUserName(username);
            // 做账号的认证
            List<User> loginUsers = userService.queryByUser(user);
            if(loginUsers != null && loginUsers.size() == 1){
                // 登录返回的用户
                User authUser = loginUsers.get(0);
                // 当前登录用户的角色
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                // 说明登录成功
                userDetails = new org.springframework.security.core.userdetails.User(
                        authUser.getUserName(),
                        authUser.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        authorities
                );
            }
        }
        return userDetails;

    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("encoder.encode(\"123\") = " + encoder.encode("123"));
    }
}
