package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wanshu.wanshu.entity.Menu;
import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.entity.RoleMenu;
import com.wanshu.wanshu.mapper.MenuMapper;
import com.wanshu.wanshu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.wanshu.service.IRoleMenuService;
import com.wanshu.wanshu.utils.Query;
import com.wanshu.wanshu.vo.MenuShowVO;
import com.wanshu.wanshu.vo.MenuVO;
import com.wanshu.wanshu.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    IRoleMenuService roleMenuService;

    @Override
    public MenuVO search(MenuVO vo) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(vo.getKey()),"title",vo.getKey())
                .orderByAsc("order_rank");
        IPage<Menu> page = this.page(
                new Query<Menu>().getPage(vo.getMap()), // 分页信息
                queryWrapper); // 查询条件
        List<Menu> list = page.getRecords().stream().map(item -> {
            if (item.getParentId() != -1) {
                // 这个菜单是二级菜单
                // 我们需要根据parentId 找到对应的父菜单的名称
                Menu parent = this.getById(item.getParentId());
                item.setParentName(parent.getTitle());
            } else {
                // 说明本身就是一级菜单
                // 不用管理
            }
            return item;
        }).collect(Collectors.toList());
        page.setRecords(list);
        vo.setPage(page);
        return vo;
    }

    @Override
    public List<Menu> getAll1LevelMenus() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",-1);
        return this.list(queryWrapper);
    }

    @Override
    public Boolean deleteMenuById(Integer id) {
        // 1.先判断是否可以删除该菜单
        // 1.1 是否分配给了 角色
        Long count = roleMenuService.getBaseMapper()
                .selectCount(new QueryWrapper<RoleMenu>().eq("menu_id", id));

        if(count == 0){
            // 1.2 判断是否是一级菜单的同时一级有了二级菜单，这种情况也不让删除
            Menu menu = this.getById(id);
            if(menu.getParentId() == -1){// 删除的菜单是一级菜单
                Long subCount = this.getBaseMapper().selectCount(new QueryWrapper<Menu>().eq("parent_id", id));
                if(subCount == 0){
                    // 表示一级菜单下没有二级菜单，可以删除
                    // 2.表示菜单没有分配给角色，可以删除
                    this.removeById(id);
                    return true;
                }
            }else{ // 删除的是二级菜单
                this.removeById(id);
                return true;
            }
        }

        return false;
    }

    /**
     * 根据用户编号查询出所有的菜单信息
     * @param username
     * @return
     */
    @Override
    public List<MenuShowVO> listByUserId(String username) {
        List<Menu> list = this.getBaseMapper().selectByUserName(username);
        if(list == null || list.size() == 0){
            return null;
        }
        // 循环找出所有的一级菜单
        List<MenuShowVO> collect = list.stream().filter(item -> {
            return item.getParentId() == -1;
        }).map(item -> {
            MenuShowVO showVO = new MenuShowVO();
            // 一级菜单属性的拷贝
            BeanUtils.copyProperties(item, showVO);
            List<Menu> subList = new ArrayList<>();
            // 根据一级菜单找到对应的二级菜单信息
            for (Menu menu : list) {
                if (menu.getParentId() == item.getId()) {
                    subList.add(menu);
                }
            }
            showVO.setSubMenus(subList);
            return showVO;
        }).collect(Collectors.toList());
        return collect;
    }
}
