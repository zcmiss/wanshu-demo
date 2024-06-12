package com.wanshu.wanshu.controller;

import com.wanshu.wanshu.service.IMenuService;
import com.wanshu.wanshu.vo.MenuShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    IMenuService menuService;

    /**
     * 登录成功后跳转到HOME页面
     * @return
     */
    @GetMapping("/home.html")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 根据当前登录用户信息查询出对应的菜单信息
        List<MenuShowVO> list = menuService.listByUserId(userDetails.getUsername());
        model.addAttribute("menus",list);
        return "home";
    }
}
