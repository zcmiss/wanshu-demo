package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.wanshu.entity.Menu;
import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.entity.RoleMenu;
import com.wanshu.wanshu.entity.UserRole;
import com.wanshu.wanshu.mapper.RoleMapper;
import com.wanshu.wanshu.service.IMenuService;
import com.wanshu.wanshu.service.IRoleMenuService;
import com.wanshu.wanshu.service.IRoleService;
import com.wanshu.wanshu.service.IUserRoleService;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.utils.Query;
import com.wanshu.wanshu.vo.MenuShowVO;
import com.wanshu.wanshu.vo.RoleMenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IRoleMenuService roleMenuService;

    @Autowired
    IMenuService menuService;

    @Override
    public PageUtils search(PageUtils vo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(vo.getKey()),"role_name",vo.getKey())
                .or().like(StringUtils.isNotBlank(vo.getKey()),"role_desc",vo.getKey())
                .orderByAsc("order_rank");
        IPage<Role> page = this.page(
                new Query<Role>().getPage(vo.getMap()), // 分页信息
                queryWrapper); // 查询条件
        vo.setPage(page);
        return vo;
    }

    /**
     * 判断当前的角色是否能够被删除
     * @param id
     * @return
     */
    @Override
    public Boolean canIDeleteById(Integer id) {
        Long userRoleCount = userRoleService.getBaseMapper().selectCount(new QueryWrapper<UserRole>().eq("role_id", id));
        Long roleMenuCount = roleMenuService.getBaseMapper().selectCount(new QueryWrapper<RoleMenu>().eq("role_id", id));
        // 如果都为0 说明这个角色没有被分配，可以删除，否则就不能被删除了
        return userRoleCount == 0 && roleMenuCount == 0 ? true:false;
    }

    @Override
    public List<Integer> queryMenuByRoleId(Integer roleId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        List<RoleMenu> list = roleMenuService.queryList(roleMenu);
        if(list!=null && list.size() > 0){
            return list.stream().map(item->{
                return item.getMenuId();
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<MenuShowVO> getMenuShow() {
        // 先查询所有的一级菜单
        // List<Role> parent_id = this.list(new QueryWrapper<Menu>().eq("parent_id", -1));
        List<Menu> parentList = menuService.list(new QueryWrapper<Menu>().eq("parent_id", -1));
        // 再根据一级菜单编号找到对应的二级菜单
        List<MenuShowVO> list = parentList.stream().map(item -> {
            MenuShowVO showVO = new MenuShowVO();
            List<Menu> subMenus = menuService.list(new QueryWrapper<Menu>().eq("parent_id", item.getId()));
            showVO.setSubMenus(subMenus);
            // 根据父菜单的编号查询出对应的信息
            BeanUtils.copyProperties(item,showVO);
            return showVO;
        }).collect(Collectors.toList());
        return list;
    }

    @Transactional
    @Override
    public void allocateMenu(RoleMenuVO vo) {
        // 1.根据角色删除菜单信息
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",vo.getRoleId()));
        // 2.分配对应的角色和菜单
        List<RoleMenu> list = vo.getMenuIds().stream().map(item -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(item);
            roleMenu.setRoleId(vo.getRoleId());
            return roleMenu;
        }).collect(Collectors.toList());
        // 批量保存角色和菜单的关系
        roleMenuService.saveBatch(list);
    }

    @Override
    public List<Role> findByRoleName(String filter) {
        return lambdaQuery().like(StringUtils.isNotBlank(filter), Role::getRoleName, filter).list();
    }

}
