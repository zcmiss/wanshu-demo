package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wanshu.flowable.modeler.constants.FlowableConstants;
import com.wanshu.flowable.modeler.domain.FlowRemoteUser;
import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.entity.UserRole;
import com.wanshu.wanshu.service.IRoleService;
import com.wanshu.wanshu.service.IUserRoleService;
import com.wanshu.wanshu.service.IUserService;
import org.flowable.ui.common.security.FlowableAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账号认证的Service
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IRoleService roleService;

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
                // 获取当前登录用户的角色信息
                UserRole userRole = new UserRole();
                userRole.setUserId(authUser.getId());
                List<UserRole> userRoleList = userRoleService.queryList(userRole);
                List<SimpleGrantedAuthority> authorities = userRoleList.stream().map(item -> {
                    Role role = roleService.getById(item.getRoleId());
                    return new SimpleGrantedAuthority(role.getRoleName());
                }).collect(Collectors.toList());

                // 配置 flowable-modeler 权限
                FlowableConstants.FLOW_ABLE_MODELER_ROLES.parallelStream().forEach(obj -> {
                    authorities.add(new SimpleGrantedAuthority(obj));
                });
                FlowRemoteUser remoteUser = new FlowRemoteUser();
                remoteUser.setId(authUser.getId()+"");
                remoteUser.setFirstName(authUser.getUserName());
                remoteUser.setPassword(authUser.getPassword());
                remoteUser.setDisplayName(authUser.getNickName());
                remoteUser.setPrivileges(new ArrayList<>(FlowableConstants.FLOW_ABLE_MODELER_ROLES));
                userDetails = new FlowableAppUser(remoteUser,user.getUserName(),authorities);

            }
        }
        return userDetails;

    }


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("encoder.encode(\"123\") = " + encoder.encode("123"));
    }
}
