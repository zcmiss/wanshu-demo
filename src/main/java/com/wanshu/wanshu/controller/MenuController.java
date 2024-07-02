package com.wanshu.wanshu.controller;

import com.wanshu.wanshu.entity.Menu;
import com.wanshu.wanshu.service.IMenuService;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/wanshu/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @GetMapping("/list")
    public String list(PageUtils vo, Model model){
        vo.setPageSize(50);
        PageUtils resVo = menuService.search(vo);
        model.addAttribute("pageUtils",resVo);
        return "sys/menu/menu";
    }

    @GetMapping("/goSaveOrUpdatePage")
    public String goSaveOrUpdatePage(@RequestParam(value = "id",required = false) Integer id,
                                     Model model){
        if(id != null){
            // 说明是更新操作
            Menu menu = menuService.getById(id);
            model.addAttribute("menu",menu);
        }
        // 找到所有的一级菜单
        List<Menu> list = menuService.getAll1LevelMenus();
        model.addAttribute("parents",list);
        return "sys/menu/menuUpdate";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Menu menu){
        String userName = new WebUtils().loginUserName();
        if(menu.getId() != null){
            // 表示更新数据
            menu.setUpdateTime(new Date());
            menu.setUpdateUser(userName);
            menuService.updateById(menu);
        }else{
            // 表示添加数据
            menu.setCreateTime(new Date());
            menu.setCreateUser(userName);
            menuService.save(menu);
        }
        return "redirect:/wanshu/menu/list";
    }

    @GetMapping("/deleteMenu/{id}")
    @ResponseBody
    public String deleteMenu(@PathVariable("id") Integer id){
        Boolean flag = menuService.deleteMenuById(id);
        return flag?"success":"error";
    }

}
