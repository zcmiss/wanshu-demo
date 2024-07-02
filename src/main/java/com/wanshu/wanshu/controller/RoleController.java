package com.wanshu.wanshu.controller;

import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.service.IRoleService;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.vo.MenuShowVO;
import com.wanshu.wanshu.vo.RoleMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@Controller
@RequestMapping("/wanshu/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/list")
    public String list(PageUtils vo, Model model){
        PageUtils resVo = roleService.search(vo);
        model.addAttribute("pageUtils",resVo);
        return "sys/role/role";
    }

    /**
     * 跳转到添加和更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/goSaveOrUpdatePage")
    public String goSaveOrUpdatePage(@RequestParam(value = "id",required = false) Integer id,
                                     Model model){
        if(id != null){
            // 需要根据编号更新角色信息
            Role role = roleService.getById(id);
            model.addAttribute("role",role);
        }
        return "sys/role/roleUpdate";
    }

    /**
     * 新增或者更新角色信息
     * @param role
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role){
        // 保存创建记录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(role.getId() != null){
            role.setUpdateTime(new Date());
            role.setUpdateUser(userDetails.getUsername());
            // 更新操作
            roleService.updateById(role);
        }else{

            role.setCreateUser(userDetails.getUsername());
            // 保存创建记录的时间
            role.setCreateTime(new Date());
            // 添加角色信息
            roleService.save(role);
        }
        return "redirect:/wanshu/role/list";
    }

    @GetMapping("/deleteRole/{id}")
    @ResponseBody
    public String deleteRole(@PathVariable("id") Integer id){
        // 检查是否能够删除该角色
        Boolean flag = roleService.canIDeleteById(id);
        if(flag){
            roleService.removeById(id);
            // 表示删除成功
            return "success";
        }
        return "error";
    }

    @GetMapping("/allocateMenu")
    public String allocateMenu(@RequestParam("id") Integer roleId,Model model){
        // 1.根据角色编号查询出所有的菜单信息
        List<Integer> menuIds = roleService.queryMenuByRoleId(roleId);
        // 2.查询出所有的菜单信息 先查询对应的一级菜单，然后根据一级菜单查询对应的二级菜单
        List<MenuShowVO> list = roleService.getMenuShow();
       // List<Role> list = roleService.list();
        model.addAttribute("roleId",roleId);
        model.addAttribute("menuIds",menuIds);
        model.addAttribute("menus",list);
        return "sys/role/allocateMenu";
    }

    @PostMapping("/saveRoleAndMenu")
    public String saveRoleAndMenu(RoleMenuVO vo){
        roleService.allocateMenu(vo);
        return "redirect:/wanshu/role/list";
    }
}
