package com.wanshu.wanshu.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.entity.UserRole;
import com.wanshu.wanshu.exception.group.SaveGroupInterface;
import com.wanshu.wanshu.exception.group.UpdateGroupInterface;
import com.wanshu.wanshu.service.IRoleService;
import com.wanshu.wanshu.service.IUserRoleService;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.Constant;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.utils.WebUtils;
import com.wanshu.wanshu.vo.UserRoleVO;
import com.wanshu.wanshu.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@Controller
@RequestMapping("/wanshu/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserRoleService userRoleService;

    /**
     * 分页查询
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list( Model model, UserVO vo){
        // 查询出所有的用户数据
        UserVO pageUtils = userService.searchPageUser(vo);
        model.addAttribute("pageUtils",pageUtils);
        return "sys/user/user";
    }

    /**
     * @Valid：开启数据校验
     * BindingResult:绑定对应的校验数据的结果
     * @Valid:不支持分组
     * @Validated:支持分组
     * @param user
     * @return
     */
    @PostMapping("/save")
    public String save(@Validated(SaveGroupInterface.class) User user,
                       Model model){
        if(StringUtils.isNotBlank(user.getPassword())){
            // 对明文密码加密处理
            user.setPassword(WebUtils.passwordEncoder(user.getPassword()));
        }
        // 设置创建时间
        user.setCreateTime(new Date());
        // 设置创建的用户 获取当前登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        user.setCreateUser(userDetails.getUsername());
        userService.save(user);
        return "redirect:/wanshu/user/list";
    }

    @GetMapping("/deleteUser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable(value = "id",required = true) Integer id){
        boolean flag = userService.removeById(id);
        return "success";
    }

    @GetMapping("/goUpdateUserPage")
    public String goUpdateUserPage(@RequestParam(value = "id",required = false) Integer id,
                                   Model model){
        if(id != null){
            User user = userService.getById(id);
            model.addAttribute("user",user);
            return "sys/user/userUpdate";
        }

        return "sys/user/userSave";
    }

    @PostMapping("/update")
    public String updateUser(@Validated(value = {UpdateGroupInterface.class}) User user){
        // 如果有更新密码

        if(StringUtils.isNotBlank(user.getPassword())){
            user.setPassword(WebUtils.passwordEncoder(user.getPassword()));
        }
        // 更新时间和更新的用户
        user.setUpdateTime(new Date());
        // 设置创建的用户 获取当前登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        user.setUpdateUser(userDetails.getUsername());
        userService.updateById(user);
        return "redirect:/wanshu/user/list";
    }

    @GetMapping("/checkUserName")
    @ResponseBody
    public String checkUserName(@RequestParam("userName") String userName){
        Boolean flag = userService.checkUserName(userName);
        return flag?"success":"error";
    }

    @GetMapping("/checkMobile")
    @ResponseBody
    public String checkMobile(@RequestParam("mobile") String mobile){
        Boolean flag = userService.checkMobile(mobile);
        return flag?"success":"error";
    }

    @GetMapping("/allocateRole")
    public String allocateRole(@RequestParam("id") Integer id,Model model){
        // 根据用户编号查询对应的角色信息
        List<Integer> roleIds = userService.getRoleByUserId(id);
        model.addAttribute("roleIds",roleIds);
        // 查询出所有的角色
        List<Role> list = roleService.list();
        model.addAttribute("roles",list);
        model.addAttribute("userId",id);
        return "sys/user/allocateRole";
    }

    /**
     * 一个用户可以分配多个角色
     *
     * @return
     */
    @PostMapping("/saveUserAndRole")
    public String saveUserAndRole(UserRoleVO vo){
        if(vo != null && vo.getRoleIds() != null){
            // 保存分配好的用户角色信息
            userService.allocateRole(vo);
        }

        return  "redirect:/wanshu/user/list";
    }
}
